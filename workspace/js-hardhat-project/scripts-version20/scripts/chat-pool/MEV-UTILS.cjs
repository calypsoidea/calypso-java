
const { ethers } = require("ethers");
const { FlashbotsBundleProvider } = require('@flashbots/ethers-provider-bundle');

async function sendFlashloanBundle() {
    // Setup providers and wallet
    const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545");
    const wallet = new ethers.Wallet("YOUR_PRIVATE_KEY", provider);
    
    console.log("Wallet address:", wallet.address);

    // Initialize Flashbots provider
    const flashbotsProvider = await FlashbotsBundleProvider.create(
        provider,
        wallet,
        "https://relay.flashbots.net",
        "mainnet"
    );

    // Get your contract instance
    const FlashBotsMultiCallFL = await ethers.getContractFactory("FlashBotsMultiCallFL");
    const flashLoanContract = FlashBotsMultiCallFL.attach("YOUR_CONTRACT_ADDRESS");

    // Define flashloan parameters
    const token0 = "0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2"; // WETH
    const token1 = "0x6B175474E89094C44Da98b954EedeAC495271d0F"; // DAI
    const amountToBorrow = ethers.utils.parseEther("10.0"); // Borrow 10 ETH

    // Prepare _params for internal logic
    const _ethAmountToCoinbase = ethers.utils.parseEther("0.01"); // Miner tip
    const _targets = [
        "0x7a250d5630B4cF539739dF2C5dAcb4c659F2488D", // Uniswap Router
    ];
    
    const _payloads = [
        "0x", // Add your encoded function calls
    ];

    const _params = ethers.utils.defaultAbiCoder.encode(
        ["uint256", "address[]", "bytes[]"],
        [_ethAmountToCoinbase, _targets, _payloads]
    );

    try {
        // Get current block
        const currentBlock = await provider.getBlock("latest");
        const blockNumber = currentBlock.number;
        const targetBlock = blockNumber + 1;

        console.log(`Current block: ${blockNumber}, Target block: ${targetBlock}`);

        // Get base fee for next block estimation (EIP-1559)
        const baseFee = await estimateNextBlockBaseFee(currentBlock);
        
        // EIP-1559 gas parameters
        const maxPriorityFeePerGas = ethers.utils.parseUnits("2", "gwei"); // Tip to miner
        const maxFeePerGas = baseFee.add(maxPriorityFeePerGas); // Max fee per gas

        console.log(`Base fee: ${ethers.utils.formatUnits(baseFee, "gwei")} Gwei`);
        console.log(`Max priority fee: ${ethers.utils.formatUnits(maxPriorityFeePerGas, "gwei")} Gwei`);
        console.log(`Max fee: ${ethers.utils.formatUnits(maxFeePerGas, "gwei")} Gwei`);

        // Create the unsigned transaction
        const unsignedTx = await flashLoanContract.populateTransaction.flashloan(
            token0,
            token1, 
            amountToBorrow,
            _params
        );

        // Estimate gas (important for Flashbots)
        const gasEstimate = await provider.estimateGas({
            ...unsignedTx,
            from: wallet.address
        });

        // EIP-1559 compliant bundle
        const bundle = [
            {
                signer: wallet,
                transaction: {
                    ...unsignedTx,
                    type: 2, // EIP-1559 transaction type
                    chainId: 1, // Mainnet
                    maxFeePerGas: maxFeePerGas,
                    maxPriorityFeePerGas: maxPriorityFeePerGas,
                    gasLimit: gasEstimate.add(50000), // Add buffer
                    nonce: await provider.getTransactionCount(wallet.address),
                }
            }
        ];

        console.log("Signing EIP-1559 bundle...");
        
        // Sign and send the bundle
        const signedBundle = await flashbotsProvider.signBundle(bundle);
        
        // Submit bundle to Flashbots
        const bundleSubmission = await flashbotsProvider.sendRawBundle(
            signedBundle, 
            targetBlock
        );

        console.log("✅ EIP-1559 Bundle submitted!");
        console.log("Bundle Hash:", bundleSubmission.bundleHash);

        // Wait for bundle to be included
        console.log("Waiting for bundle to be mined...");
        const response = await bundleSubmission.wait();
        
        if (response === 0) {
            console.log("❌ Bundle not included in target block");
        } else if (response === 1) {
            console.log("✅ Bundle included in target block!");
        } else if (response === 2) {
            console.log("🔄 Bundle included in block after target block");
        }

        // Return what you need for tracking
        return {
            bundleHash: bundleSubmission.bundleHash,
            targetBlock: targetBlock,
            maxFeePerGas: maxFeePerGas.toString(),
            maxPriorityFeePerGas: maxPriorityFeePerGas.toString(),
            gasLimit: gasEstimate.add(50000).toString(),
            status: response
        };


    } catch (error) {
        console.error("Error in flashloan bundle:", error);
        throw error;
    }
}

// Helper function to estimate next block base fee (EIP-1559)
function estimateNextBlockBaseFee(currentBlock) {
    const baseFee = currentBlock.baseFeePerGas;
    if (!baseFee) {
        return ethers.utils.parseUnits("15", "gwei"); // Fallback
    }
    
    // Simple estimation: base fee increases by ~12.5% if block is full
    // You can make this more sophisticated based on recent block data
    const estimatedBaseFee = baseFee.mul(1125).div(1000);
    return estimatedBaseFee;
}

// Alternative: Get base fee from Flashbots
async function getRecommendedFees(flashbotsProvider) {
    try {
        const feeHistory = await flashbotsProvider.getFeeHistory(
            1, // blockCount
            'latest', // lastBlock
            [25, 50, 75] // rewardPercentiles
        );
        return feeHistory;
    } catch (error) {
        console.log("Could not get fee history, using fallback");
        return null;
    }
}

// Function to check bundle status
async function checkBundleStatus(bundleHash) {
    const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545");
    const wallet = new ethers.Wallet("YOUR_PRIVATE_KEY", provider);
    
    const flashbotsProvider = await FlashbotsBundleProvider.create(
        provider,
        wallet,
        "https://relay.flashbots.net"
    );

    const bundleStats = await flashbotsProvider.getBundleStats(bundleHash, 1); // recent block
    console.log("Bundle stats:", bundleStats);
    
    return bundleStats;
}

async function sendFlashloanBundleWithBetterFees() {
    const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545");
    const wallet = new ethers.Wallet("YOUR_PRIVATE_KEY", provider);
    
    const flashbotsProvider = await FlashbotsBundleBundleProvider.create(
        provider,
        wallet,
        "https://relay.flashbots.net"
    );

    // Get current block for base fee calculation
    const currentBlock = await provider.getBlock("latest");
    const baseFee = currentBlock.baseFeePerGas;
    
    // EIP-1559 fee strategy
    const maxPriorityFeePerGas = ethers.utils.parseUnits("1.5", "gwei"); // Conservative tip
    const maxFeePerGas = baseFee.mul(2).add(maxPriorityFeePerGas); // 2x base fee + tip

    // Create EIP-1559 transaction
    const bundle = [
        {
            signer: wallet,
            transaction: {
                to: "YOUR_CONTRACT_ADDRESS",
                data: "YOUR_FLASHLOAN_CALLDATA", // Encode your flashloan call
                type: 2,
                chainId: 1,
                maxFeePerGas: maxFeePerGas,
                maxPriorityFeePerGas: maxPriorityFeePerGas,
                gasLimit: 500000,
                nonce: await provider.getTransactionCount(wallet.address),
                value: ethers.utils.parseEther("0.01") // Coinbase payment
            }
        }
    ];

    const signedBundle = await flashbotsProvider.signBundle(bundle);
    const targetBlock = currentBlock.number + 1;
    
    const bundleSubmission = await flashbotsProvider.sendRawBundle(
        signedBundle, 
        targetBlock
    );

    return {
        bundleHash: bundleSubmission.bundleHash,
        maxFeePerGas: maxFeePerGas.toString(),
        maxPriorityFeePerGas: maxPriorityFeePerGas.toString(),
        targetBlock: targetBlock
    };
}

async function main() {
    try {
        // THIS is where you get the tracking data:
        const result = await sendFlashloanBundle();
        
        // Now you can use all the tracking info:
        console.log("📦 Bundle Tracking Info:");
        console.log("Bundle Hash:", result.bundleHash);           // "0x..."
        console.log("Target Block:", result.targetBlock);         // 19234345
        console.log("Max Fee Per Gas:", result.maxFeePerGas);     // "35000000000"
        console.log("Max Priority Fee:", result.maxPriorityFeePerGas); // "1500000000"
        console.log("Gas Limit:", result.gasLimit);               // "550000"
        console.log("Status:", result.status);                    // 1 (included)
        
        // Save this for later tracking
        saveBundleForTracking(result);
        
    } catch (error) {
        console.error("Failed to send flashloan bundle:", error);
    }
}

// Function to save bundle info for later tracking
function saveBundleForTracking(bundleInfo) {
    // Save to file, database, or memory
    const trackingData = {
        bundleHash: bundleInfo.bundleHash,
        targetBlock: bundleInfo.targetBlock,
        submittedAt: new Date().toISOString(),
        fees: {
            maxFeePerGas: bundleInfo.maxFeePerGas,
            maxPriorityFeePerGas: bundleInfo.maxPriorityFeePerGas
        }
    };
    
    console.log("💾 Saved for tracking:", trackingData);
    return trackingData;
}
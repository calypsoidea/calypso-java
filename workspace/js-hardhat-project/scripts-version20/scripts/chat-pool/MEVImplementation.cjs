

const { ethers } = require("ethers");
const { FlashbotsBundleProvider } = require('@flashbots/ethers-provider-bundle');

async function sendFlashloanBundle() {
  const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545");
  const wallet = new ethers.Wallet("YOUR_PRIVATE_KEY", provider);
  
  // Flashbots provider
  const flashbotsProvider = await FlashbotsBundleProvider.create(
    provider,
    wallet,
    "https://relay.flashbots.net"
  );

  // Create the unsigned transaction
  const unsignedTx = await flashLoanContract.populateTransaction.flashloan(
    token0, 
    token1, 
    amountToBorrow, 
    _params
  );

  // Create bundle with the unsigned transaction
  const bundle = [
    {
      signer: wallet,
      transaction: {
        ...unsignedTx,
        gasPrice: ethers.utils.parseUnits("10", "gwei"),
        gasLimit: 1000000,
      }
    }
  ];

  // Send bundle to Flashbots
  const signedBundle = await flashbotsProvider.signBundle(bundle);
  const bundleHash = await flashbotsProvider.sendRawBundle(signedBundle, 1); // target block +1
  
  console.log("Bundle submitted with hash:", bundleHash);
  return bundleHash; // This is what Flashbots needs to track your bundle
}

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
        // Add your target contracts
    ];
    
    const _payloads = [
        "0x", // Add your encoded function calls
        // Example: encodeSwapFunction()
    ];

    const _params = ethers.utils.defaultAbiCoder.encode(
        ["uint256", "address[]", "bytes[]"],
        [_ethAmountToCoinbase, _targets, _payloads]
    );

    try {
        // Get current block number
        const blockNumber = await provider.getBlockNumber();
        const targetBlock = blockNumber + 1; // Target next block

        console.log(`Current block: ${blockNumber}, Target block: ${targetBlock}`);

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

        // Create the bundle with flashloan transaction
        const bundle = [
            {
                signer: wallet,
                transaction: {
                    ...unsignedTx,
                    gasLimit: gasEstimate.add(50000), // Add buffer
                    gasPrice: ethers.utils.parseUnits("30", "gwei"), // Competitive gas price
                    chainId: 1, // Mainnet
                    nonce: await provider.getTransactionCount(wallet.address),
                }
            }
        ];

        console.log("Signing bundle...");
        
        // Sign and send the bundle
        const signedBundle = await flashbotsProvider.signBundle(bundle);
        
        // Submit bundle to Flashbots
        const bundleSubmission = await flashbotsProvider.sendRawBundle(
            signedBundle, 
            targetBlock
        );

        console.log("Bundle submitted!");
        console.log("Bundle Hash:", bundleSubmission.bundleHash);

        // Wait for bundle to be included
        console.log("Waiting for bundle to be mined...");
        const response = await bundleSubmission.wait();
        
        if (response === 0) {
            console.log("❌ Bundle not included in target block");
        } else {
            console.log("✅ Bundle included in block:", response);
        }

        // Return what you need for tracking
        return {
            bundleHash: bundleSubmission.bundleHash,
            targetBlock: targetBlock,
            signedBundle: signedBundle,
            status: response === 0 ? "pending" : "included"
        };

    } catch (error) {
        console.error("Error in flashloan bundle:", error);
        throw error;
    }
}

// Execute the function
async function main() {
    try {
        const result = await sendFlashloanBundle();
        console.log("Flashloan bundle result:", result);
        
        // You can use this result to track your bundle
        // result.bundleHash - to check status with Flashbots
        // result.signedBundle - the actual transaction data
        
    } catch (error) {
        console.error("Failed to send flashloan bundle:", error);
    }
}

main();
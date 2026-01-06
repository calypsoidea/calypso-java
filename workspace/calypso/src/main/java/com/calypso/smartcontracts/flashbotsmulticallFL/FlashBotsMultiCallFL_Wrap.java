package com.calypso.smartcontracts.flashbotsmulticallFL;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.2.
 */
@SuppressWarnings("rawtypes")
public class FlashBotsMultiCallFL_Wrap extends Contract {
    public static final String BINARY = "60a06040526040516110563803806110568339810160408190526020916030565b6001600160a01b0316608052605b565b5f60208284031215603f575f5ffd5b81516001600160a01b03811681146054575f5ffd5b9392505050565b608051610fd66100805f395f818160d1015281816101e001526109200152610fd65ff3fe60806040526004361061004c575f3560e01c806310d1e85c1461005757806363ad2c41146100785780636dbf2fa0146100975780637b108610146100c057806397070d361461010b575f5ffd5b3661005357005b5f5ffd5b348015610062575f5ffd5b50610076610071366004610aad565b610132565b005b348015610083575f5ffd5b50610076610092366004610b7e565b6101b5565b6100aa6100a5366004610c26565b6103a3565b6040516100b79190610cac565b60405180910390f35b3480156100cb575f5ffd5b506100f37f000000000000000000000000000000000000000000000000000000000000000081565b6040516001600160a01b0390911681526020016100b7565b348015610116575f5ffd5b506100f373c02aaa39b223fe8d0a0e5c4f27ead9083c756cc281565b5f61013d858561042b565b90505f6101698261016461015d610155866003610497565b6103e5610515565b600161042b565b61042b565b90506101ac8285858080601f0160208091040260200160405190810160405280939291908181526020018383808284375f92019190915250869250610556915050565b50505050505050565b60405163e6a4390560e01b81526001600160a01b038581166004830152848116602483015230915f917f0000000000000000000000000000000000000000000000000000000000000000169063e6a4390590604401602060405180830381865afa158015610225573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102499190610cbe565b905073c02aaa39b223fe8d0a0e5c4f27ead9083c756cc26001600160a01b0316816001600160a01b0316630dfe16816040518163ffffffff1660e01b8152600401602060405180830381865afa1580156102a5573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102c99190610cbe565b6001600160a01b03160361033b5760405163022c0d9f60e01b81526001600160a01b0382169063022c0d9f906103099087905f9087908990600401610cd9565b5f604051808303815f87803b158015610320575f5ffd5b505af1158015610332573d5f5f3e3d5ffd5b5050505061039b565b60405163022c0d9f60e01b81526001600160a01b0382169063022c0d9f9061036d905f90889087908990600401610cd9565b5f604051808303815f87803b158015610384575f5ffd5b505af1158015610396573d5f5f3e3d5ffd5b505050505b505050505050565b60606001600160a01b0385166103b7575f5ffd5b5f5f866001600160a01b03168686866040516103d4929190610d05565b5f6040518083038185875af1925050503d805f811461040e576040519150601f19603f3d011682016040523d82523d5f602084013e610413565b606091505b509150915081610421575f5ffd5b9695505050505050565b5f806104378385610d28565b90508381101561048e5760405162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f77000000000060448201526064015b60405180910390fd5b90505b92915050565b5f825f036104a657505f610491565b5f6104b18385610d3b565b9050826104be8583610d52565b1461048e5760405162461bcd60e51b815260206004820152602160248201527f536166654d6174683a206d756c7469706c69636174696f6e206f766572666c6f6044820152607760f81b6064820152608401610485565b5f61048e83836040518060400160405280601a81526020017f536166654d6174683a206469766973696f6e206279207a65726f000000000000815250610a1c565b5f5f5f8480602001905181019061056d9190610e5d565b9250925092508051825114610580575f5ffd5b73c02aaa39b223fe8d0a0e5c4f27ead9083c756cc26001600160a01b031663a9059cbb835f815181106105b5576105b5610f2d565b6020026020010151886040518363ffffffff1660e01b81526004016105ef9291906001600160a01b03929092168252602082015260400190565b6020604051808303815f875af115801561060b573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061062f9190610f41565b505f5b82518110156106dd575f5f84838151811061064f5761064f610f2d565b60200260200101516001600160a01b031684848151811061067257610672610f2d565b60200260200101516040516106879190610f60565b5f604051808303815f865af19150503d805f81146106c0576040519150601f19603f3d011682016040523d82523d5f602084013e6106c5565b606091505b5091509150816106d3575f5ffd5b5050600101610632565b506040516370a0823160e01b81523060048201525f9073c02aaa39b223fe8d0a0e5c4f27ead9083c756cc2906370a0823190602401602060405180830381865afa15801561072d573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906107519190610f76565b90505f8461075f8784610f8d565b6107699190610f8d565b905073c02aaa39b223fe8d0a0e5c4f27ead9083c756cc2632e1a7d4d61078f8388610d28565b6040518263ffffffff1660e01b81526004016107ad91815260200190565b5f604051808303815f87803b1580156107c4575f5ffd5b505af11580156107d6573d5f5f3e3d5ffd5b505060405141925087156108fc02915087905f818181858888f19350505050158015610804573d5f5f3e3d5ffd5b50604051329082156108fc029083905f818181858888f1935050505015801561082f573d5f5f3e3d5ffd5b505f336001600160a01b0316630dfe16816040518163ffffffff1660e01b8152600401602060405180830381865afa15801561086d573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906108919190610cbe565b90505f336001600160a01b031663d21220a76040518163ffffffff1660e01b8152600401602060405180830381865afa1580156108d0573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906108f49190610cbe565b60405163e6a4390560e01b81526001600160a01b03848116600483015280831660248301529192505f917f0000000000000000000000000000000000000000000000000000000000000000169063e6a4390590604401602060405180830381865afa158015610965573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906109899190610cbe565b60405163a9059cbb60e01b81526001600160a01b0382166004820152602481018b905290915073c02aaa39b223fe8d0a0e5c4f27ead9083c756cc29063a9059cbb906044016020604051808303815f875af11580156109ea573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610a0e9190610f41565b505050505050505050505050565b5f8183610a3c5760405162461bcd60e51b81526004016104859190610cac565b505f610a488486610d52565b95945050505050565b6001600160a01b0381168114610a65575f5ffd5b50565b5f5f83601f840112610a78575f5ffd5b50813567ffffffffffffffff811115610a8f575f5ffd5b602083019150836020828501011115610aa6575f5ffd5b9250929050565b5f5f5f5f5f60808688031215610ac1575f5ffd5b8535610acc81610a51565b94506020860135935060408601359250606086013567ffffffffffffffff811115610af5575f5ffd5b610b0188828901610a68565b969995985093965092949392505050565b634e487b7160e01b5f52604160045260245ffd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610b4f57610b4f610b12565b604052919050565b5f67ffffffffffffffff821115610b7057610b70610b12565b50601f01601f191660200190565b5f5f5f5f60808587031215610b91575f5ffd5b8435610b9c81610a51565b93506020850135610bac81610a51565b925060408501359150606085013567ffffffffffffffff811115610bce575f5ffd5b8501601f81018713610bde575f5ffd5b8035610bf1610bec82610b57565b610b26565b818152886020838501011115610c05575f5ffd5b816020840160208301375f6020838301015280935050505092959194509250565b5f5f5f5f60608587031215610c39575f5ffd5b8435610c4481610a51565b935060208501359250604085013567ffffffffffffffff811115610c66575f5ffd5b610c7287828801610a68565b95989497509550505050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f61048e6020830184610c7e565b5f60208284031215610cce575f5ffd5b815161048e81610a51565b84815283602082015260018060a01b0383166040820152608060608201525f6104216080830184610c7e565b818382375f9101908152919050565b634e487b7160e01b5f52601160045260245ffd5b8082018082111561049157610491610d14565b808202811582820484141761049157610491610d14565b5f82610d6c57634e487b7160e01b5f52601260045260245ffd5b500490565b5f67ffffffffffffffff821115610d8a57610d8a610b12565b5060051b60200190565b5f82601f830112610da3575f5ffd5b8151610db1610bec82610d71565b8082825260208201915060208360051b860101925085831115610dd2575f5ffd5b602085015b83811015610e5357805167ffffffffffffffff811115610df5575f5ffd5b8601603f81018813610e05575f5ffd5b6020810151610e16610bec82610b57565b8181526040838301018a1015610e2a575f5ffd5b8160408401602083015e5f60208383010152808652505050602083019250602081019050610dd7565b5095945050505050565b5f5f5f60608486031215610e6f575f5ffd5b8351602085015190935067ffffffffffffffff811115610e8d575f5ffd5b8401601f81018613610e9d575f5ffd5b8051610eab610bec82610d71565b8082825260208201915060208360051b850101925088831115610ecc575f5ffd5b6020840193505b82841015610ef7578351610ee681610a51565b825260209384019390910190610ed3565b80955050505050604084015167ffffffffffffffff811115610f17575f5ffd5b610f2386828701610d94565b9150509250925092565b634e487b7160e01b5f52603260045260245ffd5b5f60208284031215610f51575f5ffd5b8151801515811461048e575f5ffd5b5f82518060208501845e5f920191825250919050565b5f60208284031215610f86575f5ffd5b5051919050565b8181038181111561049157610491610d1456fea2646970667358221220c9a4c9039c49d5c19c9089ca7cc1c5203b8842dc8cda1c7854381aa934f0d0b264736f6c634300081c0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_WETH_ADDRESS = "WETH_address";

    public static final String FUNC__UNISWAPFACTORY = "_uniswapFactory";

    public static final String FUNC_CALL = "call";

    public static final String FUNC_FLASHLOAN = "flashloan";

    public static final String FUNC_UNISWAPV2CALL = "uniswapV2Call";

    @Deprecated
    protected FlashBotsMultiCallFL_Wrap(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FlashBotsMultiCallFL_Wrap(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FlashBotsMultiCallFL_Wrap(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FlashBotsMultiCallFL_Wrap(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> WETH_address() {
        final Function function = new Function(FUNC_WETH_ADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> _uniswapFactory() {
        final Function function = new Function(FUNC__UNISWAPFACTORY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> call(String _to, BigInteger _value, byte[] _data,
            BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> flashloan(String token0, String token1,
            BigInteger amountToBorrow, byte[] _params) {
        final Function function = new Function(
                FUNC_FLASHLOAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token0), 
                new org.web3j.abi.datatypes.Address(160, token1), 
                new org.web3j.abi.datatypes.generated.Uint256(amountToBorrow), 
                new org.web3j.abi.datatypes.DynamicBytes(_params)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> uniswapV2Call(String sender, BigInteger amount0,
            BigInteger amount1, byte[] data) {
        final Function function = new Function(
                FUNC_UNISWAPV2CALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, sender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount0), 
                new org.web3j.abi.datatypes.generated.Uint256(amount1), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static FlashBotsMultiCallFL_Wrap load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FlashBotsMultiCallFL_Wrap(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FlashBotsMultiCallFL_Wrap load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FlashBotsMultiCallFL_Wrap(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FlashBotsMultiCallFL_Wrap load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FlashBotsMultiCallFL_Wrap(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FlashBotsMultiCallFL_Wrap load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FlashBotsMultiCallFL_Wrap(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FlashBotsMultiCallFL_Wrap> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger initialWeiValue,
            String uniswapFactory) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, uniswapFactory)));
        return deployRemoteCall(FlashBotsMultiCallFL_Wrap.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<FlashBotsMultiCallFL_Wrap> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            BigInteger initialWeiValue, String uniswapFactory) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, uniswapFactory)));
        return deployRemoteCall(FlashBotsMultiCallFL_Wrap.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<FlashBotsMultiCallFL_Wrap> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue,
            String uniswapFactory) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, uniswapFactory)));
        return deployRemoteCall(FlashBotsMultiCallFL_Wrap.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<FlashBotsMultiCallFL_Wrap> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            BigInteger initialWeiValue, String uniswapFactory) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, uniswapFactory)));
        return deployRemoteCall(FlashBotsMultiCallFL_Wrap.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor, initialWeiValue);
    }

  /*  public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }
 */
    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}

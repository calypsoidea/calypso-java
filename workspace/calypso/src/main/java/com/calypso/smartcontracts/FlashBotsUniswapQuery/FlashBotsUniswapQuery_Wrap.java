package com.calypso.smartcontracts.FlashBotsUniswapQuery;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.StaticArray3;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import com.calypso.blockchain.api.web3j.IWeb3jSmartContractWrapper;

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
public class FlashBotsUniswapQuery_Wrap extends Contract implements IWeb3jSmartContractWrapper{
    public static final String BINARY = "6080604052348015600e575f5ffd5b50610eab8061001c5f395ff3fe608060405234801561000f575f5ffd5b5060043610610034575f3560e01c80634dbf0f3914610038578063ab2217e414610068575b5f5ffd5b610052600480360381019061004d919061070f565b610098565b60405161005f91906108b0565b60405180910390f35b610082600480360381019061007d9190610965565b610283565b60405161008f9190610b02565b60405180910390f35b60605f8383905067ffffffffffffffff8111156100b8576100b7610b22565b5b6040519080825280602002602001820160405280156100f157816020015b6100de610662565b8152602001906001900390816100d65790505b5090505f5f90505b848490508110156102785784848281811061011757610116610b4f565b5b905060200201602081019061012c9190610bb7565b73ffffffffffffffffffffffffffffffffffffffff16630902f1ac6040518163ffffffff1660e01b8152600401606060405180830381865afa158015610174573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906101989190610c5e565b826dffffffffffffffffffffffffffff169250816dffffffffffffffffffffffffffff1691508063ffffffff1690508484815181106101da576101d9610b4f565b5b60200260200101515f600381106101f4576101f3610b4f565b5b6020020185858151811061020b5761020a610b4f565b5b602002602001015160016003811061022657610225610b4f565b5b6020020186868151811061023d5761023c610b4f565b5b602002602001015160026003811061025857610257610b4f565b5b6020020183815250838152508381525050505080806001019150506100f9565b508091505092915050565b60605f8473ffffffffffffffffffffffffffffffffffffffff1663574f2ba36040518163ffffffff1660e01b8152600401602060405180830381865afa1580156102cf573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102f39190610cc2565b905080831115610301578092505b83831015610344576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161033b90610d47565b60405180910390fd5b5f84846103519190610d92565b90505f8167ffffffffffffffff81111561036e5761036d610b22565b5b6040519080825280602002602001820160405280156103a757816020015b610394610684565b81526020019060019003908161038c5790505b5090505f5f90505b82811015610654575f8873ffffffffffffffffffffffffffffffffffffffff16631e3dd18b838a6103e09190610dc5565b6040518263ffffffff1660e01b81526004016103fc9190610e07565b602060405180830381865afa158015610417573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061043b9190610e4a565b90508073ffffffffffffffffffffffffffffffffffffffff16630dfe16816040518163ffffffff1660e01b8152600401602060405180830381865afa158015610486573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906104aa9190610e4a565b8383815181106104bd576104bc610b4f565b5b60200260200101515f600381106104d7576104d6610b4f565b5b602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508073ffffffffffffffffffffffffffffffffffffffff1663d21220a76040518163ffffffff1660e01b8152600401602060405180830381865afa158015610557573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061057b9190610e4a565b83838151811061058e5761058d610b4f565b5b60200260200101516001600381106105a9576105a8610b4f565b5b602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff1681525050808383815181106105f4576105f3610b4f565b5b602002602001015160026003811061060f5761060e610b4f565b5b602002019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250505080806001019150506103af565b508093505050509392505050565b6040518060600160405280600390602082028036833780820191505090505090565b6040518060600160405280600390602082028036833780820191505090505090565b5f5ffd5b5f5ffd5b5f5ffd5b5f5ffd5b5f5ffd5b5f5f83601f8401126106cf576106ce6106ae565b5b8235905067ffffffffffffffff8111156106ec576106eb6106b2565b5b602083019150836020820283011115610708576107076106b6565b5b9250929050565b5f5f60208385031215610725576107246106a6565b5b5f83013567ffffffffffffffff811115610742576107416106aa565b5b61074e858286016106ba565b92509250509250929050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f60039050919050565b5f81905092915050565b5f819050919050565b5f819050919050565b6107b2816107a0565b82525050565b5f6107c383836107a9565b60208301905092915050565b5f602082019050919050565b6107e481610783565b6107ee818461078d565b92506107f982610797565b805f5b8381101561082957815161081087826107b8565b965061081b836107cf565b9250506001810190506107fc565b505050505050565b5f61083c83836107db565b60608301905092915050565b5f602082019050919050565b5f61085e8261075a565b6108688185610764565b935061087383610774565b805f5b838110156108a357815161088a8882610831565b975061089583610848565b925050600181019050610876565b5085935050505092915050565b5f6020820190508181035f8301526108c88184610854565b905092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6108f9826108d0565b9050919050565b5f61090a826108ef565b9050919050565b61091a81610900565b8114610924575f5ffd5b50565b5f8135905061093581610911565b92915050565b610944816107a0565b811461094e575f5ffd5b50565b5f8135905061095f8161093b565b92915050565b5f5f5f6060848603121561097c5761097b6106a6565b5b5f61098986828701610927565b935050602061099a86828701610951565b92505060406109ab86828701610951565b9150509250925092565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b5f60039050919050565b5f81905092915050565b5f819050919050565b610a04816108ef565b82525050565b5f610a1583836109fb565b60208301905092915050565b5f602082019050919050565b610a36816109de565b610a4081846109e8565b9250610a4b826109f2565b805f5b83811015610a7b578151610a628782610a0a565b9650610a6d83610a21565b925050600181019050610a4e565b505050505050565b5f610a8e8383610a2d565b60608301905092915050565b5f602082019050919050565b5f610ab0826109b5565b610aba81856109bf565b9350610ac5836109cf565b805f5b83811015610af5578151610adc8882610a83565b9750610ae783610a9a565b925050600181019050610ac8565b5085935050505092915050565b5f6020820190508181035f830152610b1a8184610aa6565b905092915050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52603260045260245ffd5b5f610b86826108ef565b9050919050565b610b9681610b7c565b8114610ba0575f5ffd5b50565b5f81359050610bb181610b8d565b92915050565b5f60208284031215610bcc57610bcb6106a6565b5b5f610bd984828501610ba3565b91505092915050565b5f6dffffffffffffffffffffffffffff82169050919050565b610c0481610be2565b8114610c0e575f5ffd5b50565b5f81519050610c1f81610bfb565b92915050565b5f63ffffffff82169050919050565b610c3d81610c25565b8114610c47575f5ffd5b50565b5f81519050610c5881610c34565b92915050565b5f5f5f60608486031215610c7557610c746106a6565b5b5f610c8286828701610c11565b9350506020610c9386828701610c11565b9250506040610ca486828701610c4a565b9150509250925092565b5f81519050610cbc8161093b565b92915050565b5f60208284031215610cd757610cd66106a6565b5b5f610ce484828501610cae565b91505092915050565b5f82825260208201905092915050565b7f73746172742063616e6e6f7420626520686967686572207468616e2073746f705f82015250565b5f610d31602083610ced565b9150610d3c82610cfd565b602082019050919050565b5f6020820190508181035f830152610d5e81610d25565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610d9c826107a0565b9150610da7836107a0565b9250828203905081811115610dbf57610dbe610d65565b5b92915050565b5f610dcf826107a0565b9150610dda836107a0565b9250828201905080821115610df257610df1610d65565b5b92915050565b610e01816107a0565b82525050565b5f602082019050610e1a5f830184610df8565b92915050565b610e29816108ef565b8114610e33575f5ffd5b50565b5f81519050610e4481610e20565b92915050565b5f60208284031215610e5f57610e5e6106a6565b5b5f610e6c84828501610e36565b9150509291505056fea2646970667358221220300dffdb0fb8a5548b34df5f06351b921d86714c17dd05d981c2d9a8725cdce764736f6c634300081c0033";
    
    private static String librariesLinkedBinary;

    public static final String FUNC_GETPAIRSBYINDEXRANGE = "getPairsByIndexRange";

    public static final String FUNC_GETRESERVESBYPAIRS = "getReservesByPairs";

    @Deprecated
    protected FlashBotsUniswapQuery_Wrap(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public FlashBotsUniswapQuery_Wrap(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FlashBotsUniswapQuery_Wrap(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FlashBotsUniswapQuery_Wrap(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<List> getPairsByIndexRange(String _uniswapFactory, BigInteger _start,
            BigInteger _stop) {
        final Function function = new Function(FUNC_GETPAIRSBYINDEXRANGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _uniswapFactory), 
                new org.web3j.abi.datatypes.generated.Uint256(_start), 
                new org.web3j.abi.datatypes.generated.Uint256(_stop)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<StaticArray3<Address>>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> getReservesByPairs(List<String> _pairs) {
        final Function function = new Function(FUNC_GETRESERVESBYPAIRS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_pairs, org.web3j.abi.datatypes.Address.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<StaticArray3<Uint256>>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static FlashBotsUniswapQuery_Wrap load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FlashBotsUniswapQuery_Wrap(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FlashBotsUniswapQuery_Wrap load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FlashBotsUniswapQuery_Wrap(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public FlashBotsUniswapQuery_Wrap load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FlashBotsUniswapQuery_Wrap(contractAddress, web3j, credentials, contractGasProvider);
    }

    public FlashBotsUniswapQuery_Wrap load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FlashBotsUniswapQuery_Wrap(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FlashBotsUniswapQuery_Wrap> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FlashBotsUniswapQuery_Wrap.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<FlashBotsUniswapQuery_Wrap> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FlashBotsUniswapQuery_Wrap.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<FlashBotsUniswapQuery_Wrap> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(FlashBotsUniswapQuery_Wrap.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<FlashBotsUniswapQuery_Wrap> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(FlashBotsUniswapQuery_Wrap.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    /*
    public static void linkLibraries(List<Contract.LinkReference> references) {
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

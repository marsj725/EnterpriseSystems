package se.liu.ida.tdp024.account.rest.service;

import com.owlike.genson.Genson;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

@Path("/account")
public class AccountService {
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(),transactionEntityFacade);
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(transactionEntityFacade);
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
    @GET
    @Path("create")
    public Response create(
        @QueryParam("accounttype") String accounttype,
        @QueryParam("name") String name,
        @QueryParam("bank") String bank){
        return Response.ok().entity(this.accountLogicFacade.create(accounttype, name, bank)).build();  
    }
    @GET
    @Path("debit")
    public Response debit(
            @QueryParam("id") long id, 
            @QueryParam("amount") long debit){
            accountLogicFacade.debit(id, debit);
        return Response.ok().build();
    }
    @GET
    @Path("credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount){
            accountLogicFacade.credit(id, amount);
        return Response.ok().build();
    }
    @GET
    @Path("find/name")
    public Response findName(
            @QueryParam("name") String name){
            List<Account> account = accountLogicFacade.find(name);
            Genson genson = new Genson();
            String json = genson.serialize(account);         
        return Response.ok().entity(json).build();
    }
    @GET
    @Path("transactions")
    public Response transactions(
            @QueryParam("id") long id){
            List<Transaction> transactions = accountLogicFacade.findTransaction(id);
            Genson genson = new Genson();
            String json = genson.serialize(transactions);
        return Response.ok().entity(json).build();
    }
    
}

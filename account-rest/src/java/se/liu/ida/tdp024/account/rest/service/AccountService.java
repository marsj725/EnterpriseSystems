package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

@Path("/account")
public class AccountService {
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
    @GET
    @Path("create")
    public Response create(
            @QueryParam("accounttype") String accounttype,
            @QueryParam("name") String name,
            @QueryParam("bank") String bank){
            this.accountLogicFacade.create(accounttype, name, bank);
        return Response.ok().status(Response.Status.OK).entity("hello").build();
    }
    @GET
    @Path("debit")
    public Response debit(
            @QueryParam("debit") long id, long debit){
            //accountLogicFacade.debit(id, debit);
        return Response.ok().build();
    }
    @GET
    @Path("credit")
    public Response credit(
            @QueryParam("id") long id,
            @QueryParam("amount") long amount){
        return Response.ok().build();
    }
    @GET
    @Path("find/name")
    public Response findName(
            @QueryParam("name") String name){
        return Response.ok().build();
    }
    @GET
    @Path("transactions")
    public Response transactions(
            @QueryParam("id") long id){
        return Response.ok().build();
    }
    
}

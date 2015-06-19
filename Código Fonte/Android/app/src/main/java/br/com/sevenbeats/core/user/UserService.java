package br.com.sevenbeats.core.user;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by diogojayme on 6/5/15.
 */
public interface UserService {

//   @Headers("Content-Type: application/json")
//   @POST("/rest/usuarios/auth")
//   User auth(@Body User user);
//
//   @Headers("Content-Type: application/json")
//   @POST("/rest/usuarios/auth")
//   MockUser auth(@Body MockUser user);


   @FormUrlEncoded
   @POST("/rest/usuarios/auth")
   MockUser auth(@Field("username") String first, @Field("senha") String last);
}

package stu.cn.ua.lab4;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SpaceXApi {

    @GET("launches/past")
    Call<List<Launch>> getLaunches();
}

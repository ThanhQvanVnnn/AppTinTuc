package com.example.apptintuc.GetDataBase;

        import android.app.Application;
        import android.arch.lifecycle.LiveData;
        import android.os.AsyncTask;

        import com.example.apptintuc.Api.ApiService;
        import com.example.apptintuc.Api.BuildApi;
        import com.example.apptintuc.Object.TinTuc;

        import java.util.List;

public class FromRepository {
//    private static final String BASE_URL = "http://192.168.56.1:81/apptintuc/apiandroid.php/";
    private static final String BASE_URL = "https://themes360.net/assets/api/apiandroid.php/";
    public static ApiService getApiService(){
        return BuildApi.getClient(BASE_URL).create(ApiService.class);
    }

    public FromRepository() {
    }

    //roomdata base and livedata

    LiveData<List<TinTuc>> tintuclist;
    private getLuTinDao getLuTinDao;
    private RoomGetDB roomGetDB;

    public FromRepository(Application application) {
       roomGetDB = RoomGetDB.getDatabase(application);
       getLuTinDao = roomGetDB.getLuTinDao();
       tintuclist = getLuTinDao.getAllTinTuc();
    }
    public void insert_tintuc(TinTuc tinTuc){
        new InsertTinTucAsynctask(getLuTinDao).execute(tinTuc);
    }

    public void delete_tintuc(int id){
        new DeleteTinTucAsynctask(getLuTinDao).execute(id);
    }

    public LiveData<List<TinTuc>> getTintuclist() {
        return tintuclist;
    }

    private static class InsertTinTucAsynctask extends AsyncTask <TinTuc,Void,Void>{
        private getLuTinDao getLuTinDao;
        private InsertTinTucAsynctask(getLuTinDao getLuTinDao){
            this.getLuTinDao = getLuTinDao;
        }

        @Override
        protected Void doInBackground(TinTuc... tinTucs) {
            getLuTinDao.insert(tinTucs[0]);
            return null;
        }
    }


    private static class DeleteTinTucAsynctask extends AsyncTask <Integer,Void,Void>{
        private getLuTinDao getLuTinDao;
        private DeleteTinTucAsynctask(getLuTinDao getLuTinDao){
            this.getLuTinDao = getLuTinDao;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            getLuTinDao.delete(ids[0]);
            return null;
        }
    }


}

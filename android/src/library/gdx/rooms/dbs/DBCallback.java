package library.gdx.rooms.dbs;

public interface DBCallback<T> {
    void onCompleted(boolean success,T response);
}

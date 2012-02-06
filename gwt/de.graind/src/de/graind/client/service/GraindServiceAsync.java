package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GraindServiceAsync {

  void queryDatabaseTest(AsyncCallback<List<Integer>> callback);

}

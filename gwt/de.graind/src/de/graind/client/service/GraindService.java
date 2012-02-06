package de.graind.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

public interface GraindService extends RemoteService {
  List<Integer> queryDatabaseTest();
}

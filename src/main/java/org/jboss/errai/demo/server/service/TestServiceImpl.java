package org.jboss.errai.demo.server.service;

import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.client.shared.service.TestService;
import org.jboss.errai.demo.server.dao.UserComplaintDao;

import java.util.*;

@Service
public class TestServiceImpl implements TestService{

    @Inject
    private UserComplaintDao userComplaintDao;

    @Override
    public List<String> test(String name, String email, String text) {
        List<String> a = new ArrayList<>();
        a.add(name);
        a.add(email);
        a.add(text);
        return a;
    }
}

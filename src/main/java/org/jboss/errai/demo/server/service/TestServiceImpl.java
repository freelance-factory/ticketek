package org.jboss.errai.demo.server.service;

import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.client.shared.service.TestService;
import org.jboss.errai.demo.server.dao.UserComplaintDao;

@Service
public class TestServiceImpl implements TestService{

    @Inject
    private UserComplaintDao userComplaintDao;

    @Override
    public String test() {
        return "Hola";
    }
}

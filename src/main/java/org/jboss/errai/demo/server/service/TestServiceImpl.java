package org.jboss.errai.demo.server.service;

import java.util.List;

import javax.inject.Inject;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.errai.demo.client.shared.model.UserComplaint;
import org.jboss.errai.demo.client.shared.service.TestService;
import org.jboss.errai.demo.server.dao.UserComplaintDao;

@Service
public class TestServiceImpl implements TestService{

    @Inject
    private UserComplaintDao userComplaintDao;

    @Override
    public List<UserComplaint> getTableInfo() {
        return userComplaintDao.getAll();
    }

    @Override
    public void save(UserComplaint userComplaint) {
        userComplaintDao.create(userComplaint);
    }

    @Override
    public void delete(Long id) {
        userComplaintDao.delete(id);
    }
}

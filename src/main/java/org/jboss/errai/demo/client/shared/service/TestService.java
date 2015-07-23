package org.jboss.errai.demo.client.shared.service;

import java.util.List;

import org.jboss.errai.bus.server.annotations.Remote;
import org.jboss.errai.demo.client.shared.model.UserComplaint;

/**
 * This JAX-RS resource interface is used on both the client and the server. On
 * the server, it is implemented to expose the described resource methods as
 * HTTP endpoints. On the client, the interface can be used to construct
 * type safe remote method calls without having to worry about implementing the
 * request or serialization logic.
 */
@Remote
public interface TestService {
    public List<UserComplaint> getTableInfo();
    public void save(UserComplaint userComplaint);
}

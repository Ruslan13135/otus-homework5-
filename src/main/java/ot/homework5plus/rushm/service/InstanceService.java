package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Instance;

import java.util.List;

public interface InstanceService {

    List<Instance> getAll();

    Instance getInstance(Long instanceId);

    List<Instance> getInstances(List<String> instanceIds);

    List<Instance> getInstancesByBookId(Long bookId);

    Instance addInstance(Instance instance);

    Instance updateInstance(Instance instance);

    void deleteInstance(Long instanceId);

    long getCount();

    boolean instanceExists(Long instanceId);
}

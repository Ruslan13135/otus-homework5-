package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.exceptions.BadRequestException;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Instance;
import ot.homework5plus.rushm.repositories.BookRepository;
import ot.homework5plus.rushm.repositories.InstanceRepository;
import ot.homework5plus.rushm.service.InstanceService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InstanceServiceImpl implements InstanceService {

    private final InstanceRepository instanceRepository;
    private final BookRepository bookRepository;

    public InstanceServiceImpl(InstanceRepository instanceRepository, BookRepository bookRepository) {
        this.instanceRepository = instanceRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instance> getAll() {
        log.debug("The application got in method - getAllInstances");
        return instanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Instance getInstance(Long instanceId) {
        log.debug("The application got in method - getInstance");
        return instanceRepository.findById(instanceId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instance> getInstances(List<String> instanceIds) {
        log.debug("The application got in method - getInstances");
        List<Long> ids = instanceIds.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
        return instanceRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Instance> getInstancesByBookId(Long bookId) {
        log.debug("The application got in method - getInstancesByBookId");
        Book book = bookRepository.findById(bookId).orElse(null);
        return instanceRepository.getInstanceByBook(book);
    }

    @Override
    @Transactional
    public Instance addInstance(Instance instance) {
        log.debug("The application got in method - addInstance");
        return instanceRepository.save(instance);
    }

    @Override
    @Transactional
    public Instance updateInstance(Instance instance) {
        log.debug("The application got in method - updateInstance");
        Instance instanceDb = instanceRepository.findById(instance.getId()).orElse(null);
        if (Objects.isNull(instanceDb)) {
            throw new BadRequestException("Instance not found");
        }
        instanceDb.setInventoryNumber(instance.getInventoryNumber());
        return instanceRepository.save(instanceDb);
    }

    @Override
    @Transactional
    public void deleteInstance(Long instanceId) {
        log.debug("The application got in method - deleteInstance");
        instanceRepository.deleteById(instanceId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount");
        return instanceRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean instanceExists(Long instanceId) {
        log.debug("The application got in method - instanceExists");
        return instanceRepository.existsById(instanceId);
    }
}

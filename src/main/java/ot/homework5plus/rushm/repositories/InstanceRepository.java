package ot.homework5plus.rushm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Instance;

import java.util.List;

@Repository
public interface InstanceRepository extends JpaRepository<Instance, Long> {

    List<Instance> getInstanceByBook(Book book);
}

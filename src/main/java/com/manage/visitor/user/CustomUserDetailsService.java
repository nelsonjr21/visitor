package com.manage.visitor.user;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.manage.visitor.worker.entity.Worker;
import com.manage.visitor.worker.repository.WorkerRepository;
import com.manage.visitor.job.RoleWorkerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final WorkerRepository repository;
  private final RoleWorkerRepository roleWorkerRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Worker user = repository.findByIdentifier(username);

    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }

    return new User(
        user.getIdentifier(),
        user.getPw(),
        roleWorkerRepository.findByWorker_Identifier(user.getIdentifier()).stream()
            .map(x -> x.getRole().getCode())
            .toList()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList()));
  }
}

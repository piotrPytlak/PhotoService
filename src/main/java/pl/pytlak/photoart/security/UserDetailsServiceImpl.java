package pl.pytlak.photoart.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.pytlak.photoart.repository.UserRepository;

public class UserDerailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(()->new UsernameNotFoundException(String.format("User %s not found!", username)));
    }
}

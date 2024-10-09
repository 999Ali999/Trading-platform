package com.vinsguru.user.service.handler;

import com.vinsguru.user.UserInformation;
import com.vinsguru.user.UserInformationRequest;
import com.vinsguru.user.exceptions.UnknownUserException;
import com.vinsguru.user.repository.PortfolioItemRepository;
import com.vinsguru.user.repository.UserRepository;
import com.vinsguru.user.util.EntityMessageMapper;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInformationRequestHandler {

    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;
    private final ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder;

    public UserInformationRequestHandler(UserRepository userRepository, PortfolioItemRepository portfolioItemRepository, ThreadPoolTaskExecutorBuilder threadPoolTaskExecutorBuilder) {
        this.userRepository = userRepository;
        this.portfolioItemRepository = portfolioItemRepository;
        this.threadPoolTaskExecutorBuilder = threadPoolTaskExecutorBuilder;
    }

    public UserInformation getUserInformationRequest(UserInformationRequest request) {
        var user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UnknownUserException(request.getUserId()));

        var portfolioItems = this.portfolioItemRepository.findAllByUserId(request.getUserId());
        return EntityMessageMapper.toUserInformation(user, portfolioItems);
    }

}

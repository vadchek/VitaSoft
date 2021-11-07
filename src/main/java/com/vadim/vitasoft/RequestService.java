package com.vadim.vitasoft;

import com.vadim.vitasoft.db.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    public void createRequest(String text, String authorName, RequestStatus status) {
        Request request = new Request();
        request.setText(text);
        request.setAuthorName(authorName);
        request.setStatus(status);
        request.setTimeOfCreation(LocalDateTime.now());
        requestRepository.save(request);
    }

    public Set<Request> getRequestsByAuthorName(String authorName) {
        return requestRepository.findByAuthorName(authorName);
    }

    public Set<Request> getRequestsByStatus(RequestStatus status) {
        return requestRepository.findByStatus(status);
    }

    public Set<Request> getRequestsByAuthorNameAndStatus(String authorName, RequestStatus status) {
        return requestRepository.findByAuthorNameAndStatus(authorName, status);
    }

    public Request findById(Long id) {
        return requestRepository.findOneById(id);
    }

    public void updateRequest(Long id, String text, LocalDateTime time, RequestStatus status) {
        requestRepository.updateRequest(id, text, time, status);
    }

    public void updateRequestStatus(Long id, RequestStatus status) {
        requestRepository.updateRequestStatus(id, status);
    }
}

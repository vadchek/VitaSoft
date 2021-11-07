package com.vadim.vitasoft.web;

import com.vadim.vitasoft.Request;
import com.vadim.vitasoft.RequestService;
import com.vadim.vitasoft.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.vadim.vitasoft.User;

import java.time.LocalDateTime;

@Controller
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String userPage() {
        return "user";
    }

    @GetMapping("/operator")
    @PreAuthorize("hasAuthority('OPERATOR')")
    public String operatorPage(Model model) {
        Iterable<Request> requests = requestService.getRequestsByStatus(RequestStatus.SENT);
        for (Request r : requests) {

            char[] text = r.getText().toCharArray();
            String newText = "" + text[0];
            for (int i = 1; i < text.length; i++) {
                newText = newText + "-" + text[i];
            }
            r.setText(newText);
        }
        model.addAttribute("req", requests);
        return "operator";
    }

    @GetMapping("/consideration")
    @PreAuthorize("hasAuthority('OPERATOR')")
    public String getConsideration(@RequestParam String str, Model model) {
        Long id = Long.parseLong(str);
        Request request;
        request = requestService.findById(id);
        char[] text = request.getText().toCharArray();
        String newText = "" + text[0];
        for (int i = 1; i < text.length; i++) {
            newText = newText + "-" + text[i];
        }
        request.setText(newText);
        model.addAttribute("text", request.getText());
        model.addAttribute("name", request.getAuthorName());
        model.addAttribute("id", request.getId());
        return "consideration";
    }

    @PostMapping("/consideration")
    @PreAuthorize("hasAuthority('OPERATOR')")
    public String postConsideration(@RequestParam RequestStatus status, @RequestParam String str) {
        Long id = Long.parseLong(str);
        requestService.updateRequestStatus(id, status);
        return "redirect:/operator";
    }

    @GetMapping("/createRequest")
    @PreAuthorize("hasAuthority('USER')")
    public String createRequestPage() {
        return "createRequest";
    }

    @PostMapping("/createRequest")
    @PreAuthorize("hasAuthority('USER')")
    public String createRequest(@AuthenticationPrincipal User user, @RequestParam(defaultValue = "") String text,
                                @RequestParam(defaultValue = "DRAFT") RequestStatus status, Model model) {
        requestService.createRequest(text, user.getUsername(), status);
        return "redirect:/user";
    }

    @GetMapping("/changeDraft")
    @PreAuthorize("hasAuthority('USER')")
    public String changeDraftPage(@AuthenticationPrincipal User user, @RequestParam String str, Model model) {
        Request request;
        Long id = Long.parseLong(str);
        request = requestService.findById(id);
        model.addAttribute("text", request.getText());
        model.addAttribute("id", request.getId());
        return "changeDraft";
    }

    @PostMapping("/changeDraft")
    @PreAuthorize("hasAuthority('USER')")
    public String changeDraft(@AuthenticationPrincipal User user, @RequestParam String str, @RequestParam(defaultValue = "") String text,
                              @RequestParam(defaultValue = "DRAFT") RequestStatus status, Model model) {
        Long id = Long.parseLong(str);
        requestService.updateRequest(id, text, LocalDateTime.now(), status);
        return "redirect:/user";
    }

    @PostMapping("/listOfRequests")
    @PreAuthorize("hasAuthority('USER')")
    public String list(@AuthenticationPrincipal User user, Model model) {
        Iterable<Request> requests = requestService.getRequestsByAuthorName(user.getUsername());
        model.addAttribute("req", requests);
        return "listOfRequests";
    }

    @GetMapping("/listOfDrafts")
    @PreAuthorize("hasAuthority('USER')")
    public String listOfDrafts(@AuthenticationPrincipal User user, Model model) {
        Iterable<Request> requests = requestService.getRequestsByAuthorNameAndStatus(user.getUsername(), RequestStatus.DRAFT);
        model.addAttribute("req", requests);
        return "listOfDrafts";
    }

}

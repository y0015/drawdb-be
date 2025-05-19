package com.drawdb.controller;

import com.alibaba.fastjson2.JSONObject;
import com.drawdb.model.Diagram;
import com.drawdb.repository.DiagramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DiagramController {
    private final DiagramRepository diagramRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("getDiagram")
    public void getDiagram(String id) {
        Optional<Diagram> diagram = diagramRepository.findById("id");
        diagram.ifPresent(value -> messagingTemplate.convertAndSend("/topic/diagramData", JSONObject.parseObject(value.getContent()).toString()));
    }

    @MessageMapping("getDiagrams")
    public void getDiagrams() {
        Optional<Diagram> diagram = diagramRepository.findById("id");
        diagram.ifPresent(value -> messagingTemplate.convertAndSend("/topic/diagrams", JSONObject.parseObject(value.getContent()).toString()));
    }

    @MessageMapping("/addDiagram")
    public void addDiagram(String content) {
        Diagram existing = diagramRepository.findById("id")
                .orElse(null);
        if(existing != null){
            existing.setContent(content);
            Diagram updated = diagramRepository.save(existing);
            messagingTemplate.convertAndSend("/topic/diagrams/update", JSONObject.parseObject(updated.getContent()).toString());
        }else{
            Diagram diagram = new Diagram();
            diagram.setContent(content);
            Diagram saved = diagramRepository.save(diagram);
            messagingTemplate.convertAndSend("/topic/diagrams/add", JSONObject.parseObject(saved.getContent()).toString());
        }
    }

    @MessageMapping("/updateDiagram")
    public void updateDiagram(String content) {
        addDiagram(content);
    }

    @MessageMapping("/deleteDiagram")
    public void deleteDiagram(String id) {
        diagramRepository.deleteById(id);
        messagingTemplate.convertAndSend("/topic/diagrams/delete", id);
    }
}

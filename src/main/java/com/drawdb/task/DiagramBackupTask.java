package com.drawdb.task;

import com.drawdb.model.Diagram;
import com.drawdb.model.DiagramBackup;
import com.drawdb.repository.DiagramBackupRepository;
import com.drawdb.repository.DiagramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiagramBackupTask {
    @Autowired
    private DiagramRepository diagramRepository;
    
    @Autowired
    private DiagramBackupRepository diagramBackupRepository;
    
    @Scheduled(fixedRate = 60000) // 每分钟执行一次
    public void backupDiagram() {
        // 查询指定id的记录
        Diagram diagram = diagramRepository.findById("id").orElse(null);
        if (diagram != null) {
            // 获取最新的备份记录
            DiagramBackup latestBackup = diagramBackupRepository.findTopByOrderByBackupTimeDesc();
            
            // 如果没有备份记录，或者内容发生变化，则创建新的备份
            if (latestBackup == null || !latestBackup.getContent().equals(diagram.getContent())) {
                // 创建备份记录
                DiagramBackup backup = new DiagramBackup();
                backup.setDiagramId(diagram.getId());
                backup.setContent(diagram.getContent());
                backup.setBackupTime(LocalDateTime.now());
                diagramBackupRepository.save(backup);
                
                // 删除一周前的备份数据
                LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
                diagramBackupRepository.deleteByBackupTimeBefore(oneWeekAgo);
            }
        }
    }
}
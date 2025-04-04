package com.example.load_booking_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.load_booking_management.services.LoadService;
import java.util.List;

@RestController
@RequestMapping("/load")
public class LoadController {
    @Autowired
    private LoadService loadService;

    @PostMapping
    public ResponseEntity<Load> createLoad(@RequestBody Load load) {
        return ResponseEntity.ok(loadService.createLoad(load));
    }

    @GetMapping
    public ResponseEntity<List<Load>> getAllLoads() {
        return ResponseEntity.ok(loadService.getAllLoads());
    }

    @GetMapping("/{loadId}")
    public ResponseEntity<Load> getLoadById(@PathVariable String loadId) {
        return loadService.getLoadById(loadId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{loadId}")
    public ResponseEntity<Load> updateLoad(@PathVariable String loadId, @RequestBody Load loadDetails) {
        return ResponseEntity.ok(loadService.updateLoad(loadId, loadDetails));
    }

    @DeleteMapping("/{loadId}")
    public ResponseEntity<Void> deleteLoad(@PathVariable String loadId) {
        loadService.deleteLoad(loadId);
        return ResponseEntity.ok().build();
    }
}
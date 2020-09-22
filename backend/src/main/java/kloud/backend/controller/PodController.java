package kloud.backend.controller;

import kloud.backend.entity.KPodInfo;
import kloud.backend.service.PodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pod")
public class PodController {
    @Resource
    private PodService podService;

    @CrossOrigin
    @GetMapping("/listAll")
    public ResponseEntity<List<KPodInfo>> listAll() {
        return new ResponseEntity<>(podService.listAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/list")
    public ResponseEntity<List<KPodInfo>> listNamespace(@RequestBody Map<String, String> param) {
        String ns = param.getOrDefault("namespace", "default");
        return new ResponseEntity<>(podService.listNamespace(ns), HttpStatus.OK);
    }
}

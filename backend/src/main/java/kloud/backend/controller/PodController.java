package kloud.backend.controller;

import io.kubernetes.client.openapi.ApiException;
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
    @PostMapping("/list")
    public ResponseEntity<List<KPodInfo>> listNamespace(@RequestBody Map<String, String> param) {
        String ns = param.getOrDefault("namespace", "default");
        return new ResponseEntity<>(podService.listNamespace(ns), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Map<String, String> param) {
        String image = param.get("image");
        if (image == null) {
            return new ResponseEntity<>("Missing param \"image\"", HttpStatus.BAD_REQUEST);
        }
        String result = podService.create(image);
        if (result == null) {
            return new ResponseEntity<>("creation failed", HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody Map<String, String> param) {
        String podName = param.get("podName");
        Boolean result = podService.delete(podName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/log")
    public ResponseEntity<String> log(@RequestBody Map<String, String> param) {
        String name = param.get("podName");
        try {
            String log = podService.log(name);
            return new ResponseEntity<>(log, HttpStatus.OK);
        } catch (ApiException e) {
            return new ResponseEntity<>(HttpStatus.valueOf(e.getCode()));
        }
    }
}

package kloud.backend.controller;

import kloud.backend.service.dto.KNodeInfo;
import kloud.backend.service.NodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeController {
    @Resource
    private NodeService nodeService;

    @GetMapping("/list")
    public ResponseEntity<List<KNodeInfo>> nodeList() {
        return new ResponseEntity<>(nodeService.list(), HttpStatus.OK);
    }
}

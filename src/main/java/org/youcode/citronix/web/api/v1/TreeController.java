package org.youcode.citronix.web.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.citronix.domain.Tree;
import org.youcode.citronix.services.interfaces.TreeService;
import org.youcode.citronix.web.vm.TreeVm.TreeVM;
import org.youcode.citronix.web.vm.TreeVm.TreeResponseVM;
import org.youcode.citronix.web.vm.mapper.TreeMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/trees")
public class TreeController {

    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }


    @PostMapping("/save")
    @Operation(summary = "Save a new tree", description = "Creates a new tree and associates it with a field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tree created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TreeResponseVM> save(@RequestBody @Valid TreeVM treeVM) {
        Tree tree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.createTree(treeVM.getFieldId(), tree);
        int age = savedTree.getAge();
        double productivity = savedTree.getProductivity();
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        response.setAge(age);
        response.setProductivity(productivity);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/update/{treeId}")
    @Operation(summary = "Update an existing tree", description = "Updates the details of an existing tree by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tree updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Tree not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TreeResponseVM> update(@PathVariable UUID treeId, @RequestBody @Valid TreeVM treeVM) {
        Tree updatedTree = treeMapper.treeVMToTree(treeVM);
        Tree savedTree = treeService.updateTree(treeId, updatedTree);
        int age = savedTree.getAge();
        double productivity = savedTree.getProductivity();
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(savedTree);
        response.setAge(age);
        response.setProductivity(productivity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{treeId}")
    @Operation(summary = "Delete a tree", description = "Deletes a tree by its unique UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tree deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Tree not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> delete(@PathVariable UUID treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<>("Tree deleted successfully.", HttpStatus.OK);
    }


    @GetMapping("/find/{treeId}")
    @Operation(summary = "Find a tree by ID", description = "Retrieves the details of a tree by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tree found successfully"),
            @ApiResponse(responseCode = "404", description = "Tree not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TreeResponseVM> findById(@PathVariable UUID treeId) {
        Tree tree = treeService.getTreeById(treeId)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));
        TreeResponseVM response = treeMapper.treeToTreeResponseVM(tree);
        response.setAge(tree.getAge());
        response.setProductivity(tree.getProductivity());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/field/{fieldId}")
    @Operation(summary = "Find all trees by field ID (paginated)", description = "Retrieves a paginated list of trees associated with a specific field by its UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trees found successfully"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<TreeResponseVM>> findByField(
            @PathVariable UUID fieldId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Tree> treesPage = treeService.getTreesByField(fieldId, pageable);

        Page<TreeResponseVM> response = treesPage.map(treeMapper::treeToTreeResponseVM);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

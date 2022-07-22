package application.controllers;

import application.dto.responses.StudentGroupResponseDto;
import application.services.StudentGroupService;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentGroupControllerTest {
    @Mock
    private StudentGroupService studentGroupService;

    @InjectMocks
    private StudentGroupController studentGroupController;

    @Test
    void testGetStudentGroupResponse() {
        // Prepare
        when(studentGroupService.findAll()).thenReturn(ImmutableList.of());

        // Testing
        StudentGroupResponseDto studentGroupResponseDto = studentGroupController.getAll();

        // Validate
        verify(studentGroupService).findAll();
    }

    @Test
    void getOne() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void handleException() {
    }

    @Test
    void testHandleException() {
    }
}
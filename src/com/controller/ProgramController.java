/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

/**
 *
 * @author 25873
 */

import com.dao.ProgramDAO;
import com.model.Program;
import java.sql.SQLException;
import java.util.List;

public class ProgramController {
    private ProgramDAO dao;

    public ProgramController() {
        this.dao = new ProgramDAO();
    }

    private void validateProgram(Program program) {
        if (program.getProgramName() == null || program.getProgramName().trim().isEmpty()) {
            throw new IllegalArgumentException("Program Name cannot be empty.");
        }
        if (program.getDepartmentId() <= 0) {
            throw new IllegalArgumentException("Department ID must be valid (greater than 0).");
        }
        if (program.getDurationYears() <= 0) {
            throw new IllegalArgumentException("Duration in years must be positive.");
        }
    }

    public boolean addProgram(Program program) throws SQLException {
        validateProgram(program);
        return dao.addProgram(program);
    }

    public List<Program> getAllPrograms() throws SQLException {
        return dao.getAllPrograms();
    }

    public boolean updateProgram(Program program) throws SQLException {
        if (program.getProgramId() <= 0) {
            throw new IllegalArgumentException("Program ID must be valid for update.");
        }
        validateProgram(program);
        return dao.updateProgram(program);
    }

    public boolean deleteProgram(int programId) throws SQLException {
        if (programId <= 0) {
            throw new IllegalArgumentException("Program ID must be valid for deletion.");
        }
        return dao.deleteProgram(programId);
    }
}
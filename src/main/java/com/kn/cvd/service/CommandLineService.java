package com.kn.cvd.service;

/**
 * Service to handle command line input and output
 */
public interface CommandLineService {
    /**
     * Executes the command line interaction and returns exit code
     * @return 0 if exited correctly, non-zero code otherwise
     */
    Integer runAndReturnExitCode();
}

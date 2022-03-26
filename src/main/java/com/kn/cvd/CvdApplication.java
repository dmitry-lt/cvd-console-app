package com.kn.cvd;

import com.kn.cvd.service.CommandLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CvdApplication implements CommandLineRunner, ExitCodeGenerator {

	private final CommandLineService commandLineService;
	private int exitCode;

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(CvdApplication.class, args)));
	}

	/**
	 * Runs the default command
	 * @param args are not used in the current version
	 */
	@Override
	public void run(String... args) {
		exitCode = commandLineService.runAndReturnExitCode();
	}

	/**
	 * The exit code returned by the process
	 * @return 0 if exited correctly, non-zero code otherwise
	 */
	@Override
	public int getExitCode() {
		return exitCode;
	}
}
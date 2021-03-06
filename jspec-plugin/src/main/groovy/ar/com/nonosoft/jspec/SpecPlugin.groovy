package ar.com.nonosoft.jspec

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class SpecPlugin implements Plugin<Project> {
	void apply(Project project) {
		project.task('jspec', type: JavaExec) {
			description 'Run all test classes that inherit of Spectation abstract class'

			dependsOn project.clean
			dependsOn project.build
			project.test.enabled = false
			project.build.shouldRunAfter project.clean

			main = 'ar.com.nonosoft.jspec.ConsoleRunner'
			classpath = project.sourceSets.main.runtimeClasspath
			classpath += project.sourceSets.test.runtimeClasspath + project.files("${project.projectDir}/test")

			if (project.hasProperty("pkg")) args project.pkg
		}
	}
}

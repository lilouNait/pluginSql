pipeline {

    agent none

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        disableConcurrentBuilds()
        disableResume()
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout() //the "checkout scm" call is mandatory when this option is defined
        skipStagesAfterUnstable()
    }

    stages {
        stage("build") {
            when {
                not {
                    branch 'master'
                }
		beforeAgent true
            }
            agent {
                label "maven:3.6.3-openjdk-8"
            }
            steps {
                checkout scm
		withVersioning {
		    container ("maven"){
			sh "mvn package -B"
			sonarqubeAnalyse()
			sonarqubeCheckQualityGate()
		    	archiveArtifacts artifacts: 'target/sonar-sql-plugin.jar', followSymlinks: false
		    }
		}
            }
        }

        stage("build and release") {
            agent {
                label "maven:3.6.3-openjdk-8"
            }
            when {
                beforeAgent true
                branch 'master'
            }
            steps {
                checkout scm
                withVersioning {
                    container ("maven"){
                        sh "mvn -B org.codehaus.mojo:versions-maven-plugin:2.5:set -DnewVersion=${env.PACKAGE_VERSION}"
                        sh "mvn package -B"
                        sonarqubeAnalyse()
                        sonarqubeCheckQualityGate()			    
			mavenUpload()
			archiveArtifacts artifacts: 'target/sonar-sql-plugin.jar', followSymlinks: false
                    }
                }
            }
        }
    }
}

pipeline {
    agent any 
    stages {
    	stage('Prebuild') {
    		environment {
    	    	POSTGRES_PASSWORD = credentials('postgres_pass')
    		}
    	    steps {
		    	sh "export $POSTGRES_PASSWORD=${env.POSTGRES_PASSWORD}"
		        sh "envsubst < java-back-server/src/main/resources/application.yaml.dist > java-back-server/src/main/resources/application.yaml"
		        sh "envsubst < docker-compose.yaml.dist > docker-compose.yaml"
		    }
    	}
        stage('Build') { 
            steps {
                sh "docker-compose build"
            }
        }

        stage('Deploy') { 
            steps {
                sh "docker-compose up -d"
            }
        }
    }
}
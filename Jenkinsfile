pipeline {
    agent any 
    stages {
    	stage('Prebuild') {
    		environment {
    	    	POSTGRES_PASSWORD = credentials('postgres_pass')
    		}
    	    steps {
		    	sh "export $POSTGRES_PASSWORD=${env.POSTGRES_PASSWORD}"
		        sh "envsubst < java-back-server/src/main/resources/application.dist.yaml > java-back-server/src/main/resources/application.yaml"
		        sh "envsubst < docker-compose.dist.yaml > docker-compose.yaml"
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
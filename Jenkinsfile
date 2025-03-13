pipeline {
    agent any

    environment {
        DEPLOY_SERVER = '172.31.5.20'
        DEPLOY_USER = 'ec2-user'
        DEPLOY_PATH = '/opt/tomcat/webapps'
	SSH_KEY = '/home/ec2-user/project.pem' 
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/Black-Sparkles/NumberGuessGame.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                scp -i ${SSH_KEY} \
                target/*.war ${DEPLOY_USER}@${DEPLOY_SERVER}:${DEPLOY_PATH}/NumberGuessGame.war
                '''
            }
        }
    }
}


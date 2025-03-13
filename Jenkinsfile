pipeline {
    agent none 

    environment {
        GIT_URL = 'https://github.com/Black-Sparkles/NumberGuessGame'
        BUILD_NODE = 'build-node'
        DEPLOY_NODE = 'deploy-node'
        TOMCAT_DIR = '/home/ec2-user/apache-tomcat-9.0.102'
        WAR_NAME = 'NumberGuessGame-1.0-SNAPSHOT.war'
    }

    stages {
        stage('Checkout') {
            agent { label 'master' }
            steps {
                script {
                    echo "🔄 Checking out source code..."
                    checkout([$class: 'GitSCM', branches: [[name: 'main']], userRemoteConfigs: [[url: "${GIT_URL}"]]])
                }
            }
        }

        stage('Build') {
            agent { label "${BUILD_NODE}" }
            steps {
                script {
                    echo "🧹 Cleaning workspace before build..."
                    sh 'mvn clean'

                    echo "🛠 Compiling Java code..."
                    sh 'mvn compile'

                    echo "🚀 Building the Maven project..."
                    sh 'mvn package'

                    stash name: 'war-file', includes: 'target/NumberGuessGame-1.0-SNAPSHOT.war'
                }
            }
        }

        stage('Test') {
            agent { label BUILD_NODE }
            steps {
                script {
                    echo "🛠 Running tests..."
                    sh 'mvn test'
                }
            }
        }

        stage('Deploy') {
            agent { label DEPLOY_NODE }
            steps {
                script {
                    echo "🚀 Deploying application to Tomcat..."

                    unstash 'war-file'

                    sh """
                        if [ ! -d "${TOMCAT_DIR}" ]; then
                            echo "❌ ERROR: Tomcat directory ${TOMCAT_DIR} not found!"
                            exit 1
                        fi

                        sudo chmod +x ${TOMCAT_DIR}/bin/*.sh || echo "⚠️ Skipping chmod: No scripts found"

                        echo "🛑 Stopping Tomcat (if running)..."
                        sudo ${TOMCAT_DIR}/bin/shutdown.sh || true

                        echo "📦 Deploying WAR file..."
                        sudo mv target/NumberGuessGame-1.0-SNAPSHOT.war ${TOMCAT_DIR}/webapps/NumberGuessGame.war

                        echo "✅ Starting Tomcat server..."
                        sudo ${TOMCAT_DIR}/bin/startup.sh
                    """

                    echo "✅ Deployment successful!"
                }
            }
        }
    }

    post {
        success {
            echo "🎉 Pipeline executed successfully!"
        }
        failure {
            echo "❌ Pipeline failed. Check the logs for details."
        }
    }
}

pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'rohith0702/forex'
        DOCKER_TAG = 'latest'
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'  // ID for Docker Hub credentials
        DATABASE_URL = 'jdbc:postgresql://192.168.94.33:5432/sundaram'
        DATABASE_CREDENTIALS_ID = 'database-credentials'  // ID for database credentials
    }

    stages {
        stage('Cleanup') {
            steps {
                echo ' Cleaning workspace before starting...'
                bat 'mvn clean'
            }
        }

        stage('Checkout') {
            steps {
                echo ' Checking out code from repository...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo ' Building the application...'
                bat '''
                for /f "delims=" %%i in ('cd') do set "CURRENT_DIR=%%i"
                docker run --rm -v "%CURRENT_DIR%:/app" -w /app maven:3.9.9-eclipse-temurin-17 mvn package
                '''
            }
        }

        stage('Test') {
            steps {
                echo ' Running tests...'
                bat 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo " Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }

        stage('Push Docker Image') {
            steps {
                echo " Pushing Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"
                withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    '''
                    bat "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                echo ' Deploying services using Docker Compose...'
                bat 'docker-compose down || exit 0'  // Stops existing containers if running
                bat 'docker-compose up -d'         // Starts backend app only (PostgreSQL is local)
            }
        }

        stage('Access Database Credentials') {
            steps {
                echo 'Accessing database credentials...'
                withCredentials([usernamePassword(credentialsId: "${DATABASE_CREDENTIALS_ID}", usernameVariable: 'DB_USER', passwordVariable: 'DB_PASS')]) {
                    echo "Database Username: ${DB_USER}"
                    // Use the database credentials for your tasks, e.g., running tests, or deploying services.
                }
            }
        }
    }

    post {
        always {
            echo ' Pipeline completed!'
        }
        cleanup {
            echo ' Cleaning up containers...'
            bat 'docker stop backend_app || exit 0'
            bat 'docker rm backend_app || exit 0'
        }
        failure {
            echo ' Build failed!'
        }
        success {
            echo ' Build succeeded!'
        }
    }
}

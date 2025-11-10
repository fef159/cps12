pipeline {

    agent any

    tools {
        maven 'Maven'
        jdk 'Java'
        // git 'Git'
    }

    stages {

        stage('Checkout') {
            steps {
                echo ' Getting code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Building...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo ' Testing...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo ' Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }
                stage('Package2') {
                    steps {
                        echo ' Creating JAR...'
                        sh 'mvn package -DskipTests'
                    }
                }
    }
}

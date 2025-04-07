pipeline {
    agent none

    environment {
        SONARQUBE_URL = 'http://host.docker.internal:9000'
        SONARQUBE_TOKEN = credentials('SonarUser2')
    }

    stages {
        stage('Clean Workspace') {
            agent any
            steps {
                cleanWs()
                git branch: 'master', url: 'https://github.com/Theglassofdata/java-app-ci-cd.git'
            }
        }

        stage('Build (Java 11)') {
            agent {
                docker {
                    image 'maven:3.8.6-eclipse-temurin-11'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                sh '''
                    mvn clean package \
                    -Dmaven.compiler.source=11 \
                    -Dmaven.compiler.target=11 \
                    -Djava.version=11
                '''
                stash includes: 'target/**', name: 'compiled-artifacts'
            }
        }

        stage('Unit Test (Java 21)') {
            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-21'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                unstash 'compiled-artifacts'
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            agent {
                docker {
                    image 'maven:3.8.6-eclipse-temurin-17'
                    args '-v $HOME/.m2:/root/.m2'
                    reuseNode true
                }
            }
            steps {
                withSonarQubeEnv('SONARQUBE') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=MyProject \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN}
                    """
                }
            }
        }
    }
}
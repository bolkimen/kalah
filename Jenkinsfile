pipeline {
    agent any

    environment {
        LS = "${sh(script:'ls -lah', returnStdout: true).trim()}"
        registryCredential = 'dockehub_bolkimen'
        DOCKER_BUILDKIT = "1"
    }

    parameters {
        string(
                name: 'RELEASE_PREFIX',
                defaultValue: 'rel_',
                description: 'release prefix for container'
        )
    }

    stages {
        def img
        stage("Env Variables") {
            steps {
                echo "LS = ${env.LS}"
            }
        }

        stage('Build docker app') {
            when {
                 expression {
                     currentBuild.result == null || currentBuild.result == 'SUCCESS'
                   }
                 }
            steps {
                 sh('''#!/bin/bash -ex
                 DOCKER_BUILDKIT=1 docker build --target app \\
                 -t kalah_${BUILD_NUMBER}_${GIT_COMMIT}:latest .
                 ''')
            }
            myImg = docker.build 'kalah_${BUILD_NUMBER}_${GIT_COMMIT}:latest'
        }

        stage('Push image') {
            steps {
                script {
                    withCredentials([usernamePassword( credentialsId: registryCredential, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {

                    docker.withRegistry('https://docker.io', registryCredential) {
                        sh('''#!/bin/bash -ex
                        docker login -u ${USERNAME} -p ${PASSWORD} https://docker.io
                        docker tag kalah_${BUILD_NUMBER}_${GIT_COMMIT}:latest bolkimen/kalah:release${BUILD_NUMBER}
                        docker push bolkimen/kalah:release${BUILD_NUMBER}
                        ''')
                    }
                    }
                }
            }
        }

    }
}

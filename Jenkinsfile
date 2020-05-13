pipeline {
    agent any

    environment {
        LS = "${sh(script:'ls -lah', returnStdout: true).trim()}"
        registryCredential = 'dockehub_bolkimen'
    }

    parameters {
        string(
                name: 'RELEASE_PREFIX',
                defaultValue: 'rel_',
                description: 'release prefix for container'
        )
    }

    stages {
        stage("Env Variables") {
            steps {
                echo "LS = ${env.LS}"
            }
        }

        stage('Run tests') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t kalah_${BUILD_NUMBER}_${GIT_COMMIT}:test .
                ''')
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
        }

        stage('Push image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', registryCredential) {
                        sh('''#!/bin/bash -ex
                        docker tag kalah_${BUILD_NUMBER}_${GIT_COMMIT}:latest kalah_${BUILD_NUMBER}_${GIT_COMMIT}:release
                        docker push bolkimen/kalah
                        ''')
                    }
                }
            }
        }

    }
}

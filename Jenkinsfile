pipeline {
    agent any

    environment {
        LS = "${sh(script:'ls -lah', returnStdout: true).trim()}"
    }
//    environment {
//      registry = "bolkimen/kalah"
//      registryCredential = 'dockerhub'
//    }

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
                 -t kalah_${BUILD_NUMBER}_${GIT_COMMIT}:app .
                 ''')
            }
        }

        stage('Push image') {
            docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-bolkimen') {
                app.push("${env.BUILD_NUMBER}")
                app.push("latest")
            }
        }

//        stage('Push docker image') {
//            steps {
//                sh('''#!/bin/bash -ex
//docker tag kalah:latest kalah_${BUILD_NUMBER}_${GIT_COMMIT}:app
//docker push kalah_${BUILD_NUMBER}_${GIT_COMMIT}:app
//''')
//            }
//        }
    }
}

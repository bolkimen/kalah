pipeline {
    agent any
    environment {
        DOCKER_BUILDKIT='1'
    }

    parameters {
        string(
                name: 'RELEASE',
                defaultValue: 'Release version',
                description: 'release version'
        )
    }

    stages {
        stage('test') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t ${env.BUILD_NUMBER}:test .
                ''')
            }
        }

        stage('app') {
            when {
                 expression {
                     currentBuild.result == null || currentBuild.result == 'SUCCESS'
                   }
                 }
            steps {
                 sh('''#!/bin/bash -ex
                 DOCKER_BUILDKIT=1 docker build --target app \\
                 -t ${env.BUILD_NUMBER}:app .
                 ''')
            }
        }
    }
}

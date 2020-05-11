pipeline {
    agent any

    environment {
        GIT_BRANCH = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
    }

    parameters {
        string(
                name: 'RELEASE_PREFIX',
                defaultValue: 'rel_',
                description: 'release prefix for container'
        )
    }

    stages {
        stage('test') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t ${GIT_COMMIT}_kalah_${BRANCH_NAME}${CHANGE_ID}${BUILD_NUMBER}:test .
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
                 -t ${GIT_COMMIT}_kalah_${BRANCH_NAME}${CHANGE_ID}${BUILD_NUMBER}:app .
                 ''')
            }
        }
    }
}

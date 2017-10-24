#!/usr/bin/env groovy
def branchName = "${env.BRANCH_NAME}"
def branchNameStripped = branchName.minus("feature/")

def notifyStarted() {
  slackSend (color: '#008100',
    message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

def notifySuccessful() {
  slackSend (color: '#008100',
    message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

def notifyFailed() {
  slackSend (color: '#FF0000',
    message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
}

node {

  try {
    notifyStarted()

    stage ('Checkout')
    {
      checkout scm
    }

    stage ('JUnit')
    {
      sh "./gradlew clean test"
      junit allowEmptyResults: true, testResults: 'build/test-results/test/TEST-*.xml'
    }

    stage ('Package')
    {
      sh "./gradlew clean jar -PbranchName=${branchNameStripped} -PbuildNumber=${env.BUILD_NUMBER}"
      archive 'build/libs/*.jar'
    }
    notifySuccessful()
  }
  catch (exception)
  {
    notifyFailed()
    throw exception
  }
}

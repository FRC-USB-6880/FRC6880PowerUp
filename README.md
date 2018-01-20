# FRC6880PowerUp
Team 6880 Software for the PowerUp season
=========================================

How to setup your Desktop/Laptop to work on FRC Java software
-------------------------------------------------------------

1) Install Eclipse IDE for Java and Install WPI plugin for Eclipse.

    See the instructions at:
    http://wpilib.screenstepslive.com/s/currentCS/m/java/l/599681-installing-eclipse-c-java

2) After you have successfully installed the WPI plugin for Eclipse,
    you should see the following directory:

    Windows:  C:\Users'AccountName'\wpilib\user\java\lib\

    Mac:  /Users/<username>/wpilib/user/java/lib/
    
3)  Download the following jar files and copy them to the directory specified in step 2.

    a) navx_frc.jar
    
    General Instructions: https://www.pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/

    Windows download: https://www.kauailabs.com/public_files/navx-mxp/navx-mxp.zip

    Mac download: https://www.kauailabs.com/public_files/navx-mxp/navx-mxp-libs.zip
    
    Unzip the zip file and you will see navx_frc.jar file.

    b) json-simple-1.1.1.jar
    
    Windows and Mac download: 

    https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/json-simple/json-simple-1.1.1.jar
    
    c) CTRE_Phoenix.jar, CTRE_Phoenix-sources.jar, libCTRE_PhoenixCCI.so
    
    General Instructions:
    https://github.com/CrossTheRoadElec/Phoenix-Documentation
    
    Windows download:  
    http://www.ctr-electronics.com/downloads/installers/CTRE%20Phoenix%20Framework%20v5.2.1.1.zip
    
    Mac download:
    http://www.ctr-electronics.com//downloads/lib/CTRE_Phoenix_FRCLibs_NON-WINDOWS_v5.2.1.1.zip

4)  Clone FRC6880PowerUp repository into your Eclipse workspace.  This can be done in several ways.

    a)  File -> Import... -> Repositories from GitHub -> Enter "FRC6880PowerUp" and search.
    b)  Instead of the above method, you can use git bash to clone the repository.
        i)  Open Git Bash from Windows Start menu.
        ii) cd to your workspace directory.  This should be typically /c/Users/<username>/My Documents/workspace/
        iii) Enter the following command:
            git clone https://github.com/FRC-USB-6880/FRC6880PowerUp.git
    c)  You can also use "Git Repositories" view in Eclipse to clone a git repository.

5)  If the FRC6880PowerUp project does not show up in your "Package Explorer" view,
    open it from the file system:

    File -> Open Projects from Filesystem... -> Directory -> Select the FRC6880PowerUp/ directory where you cloned the repository above.

6)  You should be all set for now.  Those programming the TalonSRX motor controllers may want to setup
    the javadoc using the procedure described in:

    https://github.com/CrossTheRoadElec/Phoenix-Documentation#add-javadoc-if-using-java  

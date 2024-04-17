
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# Sets the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)
set(CMAKE_CXX_STANDARD 17)

# Declares the project name. The project name can be accessed via ${ PROJECT_NAME},
# Since this is the top level CMakeLists.txt, the project name is also accessible
# with ${CMAKE_PROJECT_NAME} (both CMake variables are in-sync within the top level
# build script scope).
project("gdx-skia")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#
# In this top level CMakeLists.txt, ${CMAKE_PROJECT_NAME} is used to define
# the target library name; in the sub-module's CMakeLists.txt, ${PROJECT_NAME}
# is preferred for the same purpose.
#
# In order to load a library into your app from Java/Kotlin, you must call
# System.loadLibrary() and pass the name of the library defined here;
# for GameActivity/NativeActivity derived applications, the same library name must be
# used in the AndroidManifest.xml file.


# Assuming the path to Skia's headers and libraries

include_directories(${CMAKE_SOURCE_DIR}/../skia/arm64-v8a)
link_directories(${CMAKE_SOURCE_DIR}/../skia/arm64-v8a/out)

file(GLOB_RECURSE cc_sources "*.cc")
file(GLOB_RECURSE hh_sources "*.hh")

set(cpp_srcs ${cc_sources} ${hh_sources})

add_executable(${CMAKE_PROJECT_NAME} ${cpp_srcs})

# Link against the Skia library
target_link_libraries(${CMAKE_PROJECT_NAME} libskia.a)

target_compile_options(${CMAKE_PROJECT_NAME} PRIVATE
        -DANDROID  # Define ANDROID to enable Android-specific features in your code
        -Wall      # Enable all warnings
)

# Set C++ standard and optimization flags
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -fexceptions -frtti -O2")

#target_link_libraries(${CMAKE_PROJECT_NAME}
#        # List libraries link to the target library
#        skia
#)

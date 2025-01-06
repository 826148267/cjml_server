Thank you for your attention to this project ~  
Please select your language:
- [English](README.md)
- [中文](README_zh.md)

# Secure Storage System

## Introduction

This project is a secure storage system based on integrity auditing technology and ciphertext deduplication, aiming to provide a safe and reliable file storage and management solution. The system adopts a lightweight deduplication ciphertext integrity audit method to ensure the security and integrity of user data.

##  Properties

- **Secure Storage**: All files are encrypted to ensure data security during transmission and storage.
- **Cryptotext deduplication**: The system supports file deduplication, saving storage space and improving storage efficiency.
- **Integrity Audit**: Through ciphertext integrity audit, ensure the integrity of the file during storage and prevent data tampering.
- **Privacy Information Retrieval**: Through inadvertent access, the user's access behavior can also be guaranteed to be confidential. Its security is higher than the commonly discussed semantic security.
- **User-friendly interface**: Provides an intuitive and easy-to-use user interface to facilitate users to upload, download and manage files.

## Privacy-preserving information retrieval
Design based on the OSU protocol in the following article.
> [Enabling_Efficient_Secure_and_Privacy-Preserving_Mobile_Cloud_Storage.pdf](https://github.com/user-attachments/files/18230817/Enabling_Efficient_Secure_and_Privacy-Preserving_Mobile_Cloud_Storage.pdf)

## Cryptotext deduplication

Ciphertext deduplication is a technology that deduplicates encrypted data, aiming to reduce storage space while maintaining data security. Here's how it works:

1. **Encryption processing**: Before the file is uploaded, the system will encrypt the file and generate ciphertext.
2. **Hash calculation**: The system hashes the ciphertext and generates a unique hash value. Files with the same content will generate the same ciphertext and hash value after encryption.
3.  **Deduplicated Storage**: Before storing a new file, the system checks whether the hash of the file already exists. If it exists, the system will no longer store a copy of the file, but only keep a reference to the original file. This approach significantly reduces storage requirements while ensuring data security.

## Integrity Audit

Integrity auditing is a technique that ensures data has not been tampered with during storage and transmission. Its main steps include:

1. **Data Encryption**: Before the file is uploaded, the system will encrypt the file to ensure the security of the data during transmission.
2. **Hash Verification**: When the file is uploaded, the system will generate the hash value of the file and store it in the database. The hash value is used for subsequent integrity verification.
3. **Periodic audit**: The system will regularly conduct integrity audits on stored files. By recalculating the file's hash and comparing it to the stored hash, the system can detect if the file has been tampered with.
4. **Alert mechanism**: If it is found that the hash value of the file does not match the stored hash value, the system will trigger an alert to notify the administrator for further investigation and processing.

##  System overall architecture diagram
<p align="center">
<img width="416" alt="image" src="https://github.com/user-attachments/assets/21fd4a8d-c8ed-4d8b-827a-e12cc5ec65d1" />
</p>
The client system is built using javafx. The backend uses an application server based on the springboot framework. There are 4 modules in the backend, namely gdbigdate-ldcia-server-v2, gdbigdata-access-middle-server-v2 and gdbigdata-access-real-server-v2, gdbigdata-user-auth

##  Project directory

```plaintext
cjml_server
├── bigdata
│   ├── gdbiddate-ldcia-server-v2
│   ├── gdbigdata-access-middle-server-v2
│   ├── gdbigdata-access-real-server-v2
│   ├── gdbigdata-audit-csp-server
│   ├── gdbigdata-audit-tpa-server
│   ├── gdbigdata-dupless-csp-server
│   ├── gdbigdata-dupless-ks-server
│   ├── gdbigdata-eureka-17000
│   ├── gdbigdata-gateway-17001
│   ├── gdbigdata-tempserver
│   └── gdbigdate-user-auth
└── desktop
```

##  Application functions
The project mainly includes the following applications:

1. **gdbiddate-ldcia-server-v2**: Mainly handles data auditing and integrity verification.
2. **gdbigdata-access-middle-server-v2**: As an intermediate server, connect the client and the actual data storage server.
3. **gdbigdata-access-real-server-v2**: Handles real data storage and retrieval.
4. **gdbigdata-audit-csp-server**: Mainly handles integrity certification and auditing of data storage.
5. **gdbigdata-audit-tpa-server**: Handles TPA (third-party audit) related functions.
6. **gdbigdata-dupless-csp-server**: Handles data deduplication and storage tasks.
7. **gdbigdata-dupless-ks-server**: Handles key management and secure storage.
8. **gdbigdata-eureka-17000**: Service registration and discovery module.
9. **gdbigdata-gateway-17001**: API gateway module.
10. **gdbigdata-tempserver**: Temporary server, used for testing and development.
11. **gdbigdate-user-auth**: User authentication module.
12. **desktop**: Desktop client application.

## Profile style

Each module has its own configuration file, usually located in the `src/main/resources` directory:

- `application.yml`: Global configuration file.
- `application-dev.yml`: Development environment configuration file.
- `application-prod.yml`: Production environment configuration file.

Modify the appropriate configuration files as needed to suit your local or production environment.

## Server-side deployment instructions
| Server list to be deployed |
| :------: |
| gdbigdate-ldcia-server-v2 |
| gdbigdata-access-middle-server-v2 |
| gdbigdata-access-real-server-v2 |
| gdbigdata-user-auth |
| Mysql 8.0.27 |
| redis |

### Server-side code
[https://github.com/826148267/cjml_server/tree/master/bigdata](https://github.com/826148267/cjml_server/tree/master/bigdata)

### MySQL

```bash
docker run --name gdbd-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --mount type=bind,src=/home/docker/mysql/conf/my.cnf,dst=/etc/mysql/my.cnf --mount type=bind,src=/media/Yang/DATA/mysql/datadir,dst=/var/lib/mysql --restart=on-failure:3 -d mysql:8.0.27
```

### Redis

```bash
docker run -p 30060:30060 --name myredis -v/home/Yang/桌面/docker-redis/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf
```

### gdbigdata-user-auth

```bash
#Basic image
FROM openjdk:17-oracle
#Maintainer, usually write name + email
MAINTAINER gzf<zeavango@gmail.com>
#Set environment variables when building
#ENV
#Copy the jar package to the image, the first variable is
ADD gdbigdate-user-auth-1.0-SNAPSHOT.jar /gdbigdata/userauth/gdbigdate-user-auth-1.0-SNAPSHOT.jar
#Specify the command to start when the container starts
#ENTRYPOINT ["mkdir","/gdbigdata/userauth/log"]
#ENTRYPOINT ["cd","/gdbigdata/userauth"]
#working directory
#WORKDIR /gdbigdata/accessrealserver
#Container volume The main reason is that the operation and maintenance personnel forget -v. With it, it will be mounted anonymously without being written randomly to the storage layer of the container.
VOLUME ["/gdbigdata/userauth"]
# is what we usually write -p
EXPOSE 10003
#Commands that need to be run when the mirror is running
CMD ["java","-jar","/gdbigdata/userauth/gdbigdate-user-auth-1.0-SNAPSHOT.jar","&"]
```

### gdbigdate-ldcia-server-v2

```bash
#Basic image
FROM openjdk:17-oracle
#Maintainer, usually write name + email
MAINTAINER gzf<zeavango@gmail.com>
#Set environment variables when building
#ENV
#Copy the jar package to the image, the first variable is
ADD target/ldcia-server-v2.jar /gdbigdata/ldcia/ldcia-server-v2.jar
ADD src/main/resources/a.properties /gdbigdata/ldcia/a.properties
#Specify the command to start when the container starts
#ENTRYPOINT ["mkdir","/gdbigdata/ldcia/log"]
#ENTRYPOINT ["cd","/gdbigdata/ldcia"]
#working directory
#WORKDIR /gdbigdata/ldcia
#Container volume The main reason is that the operation and maintenance personnel forget -v. With it, it will be mounted anonymously without being written randomly to the storage layer of the container.
VOLUME ["/gdbigdata/ldcia"]
# is what we usually write -p
EXPOSE 10004
#Commands that need to be run when the mirror is running
CMD ["java","-jar","/gdbigdata/ldcia/ldcia-server-v2.jar","&"]
```

### gdbigdata-access-real-server-v2
```bash
#Basic image
FROM openjdk:17-oracle
#Maintainer, usually write name + email
MAINTAINER gzf<zeavango@gmail.com>
#Set environment variables when building
#ENV
#Copy the jar package to the image, the first variable is
ADD target/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar /gdbigdata/accessrealserver/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar
#Specify the command to start when the container starts
#ENTRYPOINT ["mkdir","/gdbigdata/accessrealserver/log"]
#ENTRYPOINT ["cd","/gdbigdata/accessrealserver"]
#working directory
#WORKDIR /gdbigdata/accessrealserver
#Container volume The main reason is that the operation and maintenance personnel forget -v. With it, it will be mounted anonymously without being written randomly to the storage layer of the container.
VOLUME ["/gdbigdata/accessrealserver"]
# is what we usually write -p
EXPOSE 10001
#Commands that need to be run when the mirror is running
CMD ["java","--add-opens=java.base/java.lang=ALL-UNNAMED","-jar","/gdbigdata/accessrealserver/gdbigdata-access-real-server-v2-1.0-SNAPSHOT.jar","&"]
```

### gdbigdata-access-middle-server-v2

```bash
#Basic image
FROM openjdk:17-oracle
#Maintainer, usually write name + email
MAINTAINER gzf<zeavango@gmail.com>
#Set environment variables when building
#ENV
#Copy the jar package to the image, the first variable is
ADD target/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar /gdbigdata/accessmiddleserver/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar
#Specify the command to start when the container starts
#ENTRYPOINT ["mkdir","/gdbigdata/accessrealserver/log"]
#ENTRYPOINT ["cd","/gdbigdata/accessrealserver"]
#working directory
#WORKDIR /gdbigdata/accessrealserver
#Container volume The main reason is that the operation and maintenance personnel forget -v. With it, it will be mounted anonymously without being written randomly to the storage layer of the container.
VOLUME ["/gdbigdata/accessmiddleserver"]
# is what we usually write -p
EXPOSE 10002
#Commands that need to be run when the mirror is running
CMD ["java","-jar","/gdbigdata/accessmiddleserver/gdbigdata-access-middle-server-v2-1.0-SNAPSHOT.jar","&"]
```

##  installation

1.  clone the project locally:
```bash
git clone git@github.com/826148267/cjml_server.git
```

2. Enter the project directory:
```bash
cd gdbd-desktop
```

3.  installation dependencies:
Java9 or above environment (required for modular programming, preferably Java17)

4.  Start the system:
After compiling and running, just click to run the software before

##  use

- Users can use the system by registering an account.
- After logging in, users can upload files, and the system will automatically encrypt and deduplicate them.
- Users can download their own files at any time and the system will ensure file integrity.

##  function display

### private information retrieval
<p align="center">
<img width="415" alt="image" src="https://github-production-user-asset-6210df.s3.amazonaws.com/33170232/398193707-0670e127-a61d-43db-b59f-1fa6eb8cb3a9.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250106%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250106T053439Z&X-Amz-Expires=300&X-Amz-Signature=8c3d357cbfb4b86c52cb8c610e52eb640cec9349d67d4438c26355871f610700&X-Amz-SignedHeaders=host" />
</p>
<p align="center">
<img width="415" alt="image" src="https://github-production-user-asset-6210df.s3.amazonaws.com/33170232/398193740-498a32fe-a71b-48ae-96df-4fe54dbe1849.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250106%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250106T053524Z&X-Amz-Expires=300&X-Amz-Signature=9ccfb4b92e27947f69a3a71f131293efd52ab6697db9f9e6a41a78c3fecae548&X-Amz-SignedHeaders=host" />
</p>
<p align="center">
<img width="416" alt="image" src="https://github-production-user-asset-6210df.s3.amazonaws.com/33170232/398193792-efc3518a-1c16-4ff0-a904-83d207dabb6f.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250106%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250106T053557Z&X-Amz-Expires=300&X-Amz-Signature=7192d2c0a55c2e2c2cd9a1c7f91bfdb4156d59c82c6353f40ff97b6585bc56bf&X-Amz-SignedHeaders=host" />
</p>
<p align="center">
<img width="416" alt="image" src="https://github-production-user-asset-6210df.s3.amazonaws.com/33170232/398193821-d19591b0-bc69-4106-9a35-e8c49ea0a28c.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250106%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250106T053808Z&X-Amz-Expires=300&X-Amz-Signature=d13d20b711a5297066c1e41d9caade5564745e9c111ded9da69dc7cee55a7063&X-Amz-SignedHeaders=host" />
</p>

###  Ciphertext deduplication related functions
<p align="center">
<img width="412" alt="image" src="https://github.com/user-attachments/assets/95f4c0cc-b245-4e40-8a84-fe2e18520f67" />
</p>
<p align="center">
<img width="414" alt="image" src="https://github.com/user-attachments/assets/b87a3421-6c02-4c25-a250-78520c50a6d8" />
</p>

###  integrity audit related functions
<p align="center">
<img width="416" alt="image" src="https://github.com/user-attachments/assets/d6ab0f67-2851-4791-9099-8868ed0c5186" />
</p>
<p align="center">
<img width="416" alt="image" src="https://github.com/user-attachments/assets/1b9235b0-45fe-4272-8366-1db69dbdf242" />
</p>

## Principle
<p align="center">
<img width="482" alt="image" src="https://github.com/user-attachments/assets/0938d4e8-0434-4a31-836c-7c46967989c1" />
</p>
<p align="center">
<img width="482" alt="image" src="https://github.com/user-attachments/assets/b1633400-9662-4d4f-a60b-3740e845eb70" />
</p>
<p align="center">
<img width="482" alt="image" src="https://github.com/user-attachments/assets/965d215a-6607-4db2-9232-bf0217be5038" />
</p>
A lightweight deduplication ciphertext integrity audit solution is characterized by including three roles: data owner, auditor and storage service provider.

The method is roughly divided into three stages:

1. **Initialization Phase**:
- After the data owner is initialized, the corresponding data is sent to the storage service provider and auditor.

2. **File Upload Phase**:
- After the user preprocesses the original data, the preprocessed data is sent to the storage service provider.
- The storage service provider responds, and the data owner determines whether the tag should be calculated based on the response results.
- If calculation is needed, generate labels; otherwise, skip the label generation step and directly generate label conversion auxiliary materials and audit materials and send them to the storage service provider.

3. **Audit Phase**:
- The auditor sends a challenge to the target file to the storage service provider.
- The storage service provider responds to the challenge, and the auditor verifies the response results.

The build method includes the following steps:

1. **Initialization Phase**:
- Initialization of user keys, initialization of public and private key pairs for auditing, etc.

2. **File upload stage**:
- Generate a deduplication key and encrypt the original file.
- Divide and encode encrypted files.
- Encryption of deduplication keys, upload encrypted, diced and encoded files and encrypted deduplication keys.
- Calculate and upload integrity labels (if necessary), label conversion auxiliary materials, and audit materials to storage service providers for block data.

3. **Audit Phase**:
- The auditor sends the challenge and verifies the correctness of the response to the challenge.
- The storage service provider uses the data it holds to calculate the response to the challenge.
  
## Formula and correctness
> [Lightweight deduplication ciphertext integrity audit method.docx](https://github.com/user-attachments/files/18230674/_v2.docx)

## Related dependencies
- Spring Boot
- Spring Data JPA
- Spring Cloud
- MySQL Connector
- Redis
- Swagger
- Fastjson
- JUnit
- Lombok

## Contributed

Contributions of any kind are welcome! Please submit issues, suggestions, or pull requests.

## License

This project is licensed under the Apache license 2.0, see the [LICENSE](LICENSE.md) file for details.

## Contact

If you have any questions, please contact [zeavango@gmail.com] or visit [https://github.com/826148267]

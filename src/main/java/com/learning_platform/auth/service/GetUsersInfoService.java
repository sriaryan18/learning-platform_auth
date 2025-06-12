package com.learning_platform.auth.service;

import com.learning_platform.auth.models.User;
import com.learning_platform.auth.repository.UserRepository;
import com.learning_platform.grpc.GetUsersInfoGrpc.GetUsersInfoImplBase;
import com.learning_platform.grpc.GetUsersInfoReply;
import com.learning_platform.grpc.GetUsersInfoRequest;
import com.learning_platform.grpc.UserInfo;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.stream.Collectors;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GetUsersInfoService extends GetUsersInfoImplBase {

  private final UserRepository userRepository;

  public GetUsersInfoService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void getUsersInfo(
      GetUsersInfoRequest request, StreamObserver<GetUsersInfoReply> responseObserver) {
    List<User> users = (List<User>) userRepository.findAllById(request.getUserIdsList());

    List<UserInfo> userInfos =
        users.stream()
            .map(
                (User user) ->
                    UserInfo.newBuilder()
                        .setId(user.getId())
                        .setUsername(user.getUsername())
                        .setEmail(user.getEmail())
                        .setRole(user.getRole().name())
                        .setOrganizationId(user.getOrganizationId())
                        .setCreatedAt(user.getCreatedAt().toString())
                        .build())
            .collect(Collectors.toList());

    responseObserver.onNext(GetUsersInfoReply.newBuilder().addAllUserInfo(userInfos).build());
    responseObserver.onCompleted();
  }
}

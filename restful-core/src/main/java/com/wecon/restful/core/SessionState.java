// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SessionState.proto

package com.wecon.restful.core;

public final class SessionState {
  private SessionState() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface UserInfoOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required int64 userID = 1;
    /**
     * <code>required int64 userID = 1;</code>
     */
    boolean hasUserID();
    /**
     * <code>required int64 userID = 1;</code>
     */
    long getUserID();

    // required string cuid = 2;
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    boolean hasCuid();
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    java.lang.String getCuid();
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    com.google.protobuf.ByteString
        getCuidBytes();

    // required int32 productId = 3;
    /**
     * <code>required int32 productId = 3;</code>
     *
     * <pre>
     *客户端应用ID
     * </pre>
     */
    boolean hasProductId();
    /**
     * <code>required int32 productId = 3;</code>
     *
     * <pre>
     *客户端应用ID
     * </pre>
     */
    int getProductId();

    // required int64 loginTime = 4;
    /**
     * <code>required int64 loginTime = 4;</code>
     *
     * <pre>
     *登录时间
     * </pre>
     */
    boolean hasLoginTime();
    /**
     * <code>required int64 loginTime = 4;</code>
     *
     * <pre>
     *登录时间
     * </pre>
     */
    long getLoginTime();

    // optional int64 loginIp = 5;
    /**
     * <code>optional int64 loginIp = 5;</code>
     *
     * <pre>
     *登录IP
     * </pre>
     */
    boolean hasLoginIp();
    /**
     * <code>optional int64 loginIp = 5;</code>
     *
     * <pre>
     *登录IP
     * </pre>
     */
    long getLoginIp();

    // optional string account = 6;
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    boolean hasAccount();
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    java.lang.String getAccount();
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    com.google.protobuf.ByteString
        getAccountBytes();

    // required int32 userType = 7;
    /**
     * <code>required int32 userType = 7;</code>
     *
     * <pre>
     *用户类型
     * </pre>
     */
    boolean hasUserType();
    /**
     * <code>required int32 userType = 7;</code>
     *
     * <pre>
     *用户类型
     * </pre>
     */
    int getUserType();
  }
  /**
   * Protobuf type {@code com.wecon.restful.core.UserInfo}
   */
  public static final class UserInfo extends
      com.google.protobuf.GeneratedMessage
      implements UserInfoOrBuilder {
    // Use UserInfo.newBuilder() to construct.
    private UserInfo(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private UserInfo(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final UserInfo defaultInstance;
    public static UserInfo getDefaultInstance() {
      return defaultInstance;
    }

    public UserInfo getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private UserInfo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              userID_ = input.readInt64();
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              cuid_ = input.readBytes();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              productId_ = input.readInt32();
              break;
            }
            case 32: {
              bitField0_ |= 0x00000008;
              loginTime_ = input.readInt64();
              break;
            }
            case 40: {
              bitField0_ |= 0x00000010;
              loginIp_ = input.readInt64();
              break;
            }
            case 50: {
              bitField0_ |= 0x00000020;
              account_ = input.readBytes();
              break;
            }
            case 56: {
              bitField0_ |= 0x00000040;
              userType_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.wecon.restful.core.SessionState.internal_static_com_wecon_restful_core_UserInfo_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.wecon.restful.core.SessionState.internal_static_com_wecon_restful_core_UserInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.wecon.restful.core.SessionState.UserInfo.class, com.wecon.restful.core.SessionState.UserInfo.Builder.class);
    }

    public static com.google.protobuf.Parser<UserInfo> PARSER =
        new com.google.protobuf.AbstractParser<UserInfo>() {
      public UserInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new UserInfo(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<UserInfo> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required int64 userID = 1;
    public static final int USERID_FIELD_NUMBER = 1;
    private long userID_;
    /**
     * <code>required int64 userID = 1;</code>
     */
    public boolean hasUserID() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int64 userID = 1;</code>
     */
    public long getUserID() {
      return userID_;
    }

    // required string cuid = 2;
    public static final int CUID_FIELD_NUMBER = 2;
    private java.lang.Object cuid_;
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    public boolean hasCuid() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    public java.lang.String getCuid() {
      java.lang.Object ref = cuid_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          cuid_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string cuid = 2;</code>
     *
     * <pre>
     *登录设备的CUID
     * </pre>
     */
    public com.google.protobuf.ByteString
        getCuidBytes() {
      java.lang.Object ref = cuid_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cuid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required int32 productId = 3;
    public static final int PRODUCTID_FIELD_NUMBER = 3;
    private int productId_;
    /**
     * <code>required int32 productId = 3;</code>
     *
     * <pre>
     *客户端应用ID
     * </pre>
     */
    public boolean hasProductId() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>required int32 productId = 3;</code>
     *
     * <pre>
     *客户端应用ID
     * </pre>
     */
    public int getProductId() {
      return productId_;
    }

    // required int64 loginTime = 4;
    public static final int LOGINTIME_FIELD_NUMBER = 4;
    private long loginTime_;
    /**
     * <code>required int64 loginTime = 4;</code>
     *
     * <pre>
     *登录时间
     * </pre>
     */
    public boolean hasLoginTime() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>required int64 loginTime = 4;</code>
     *
     * <pre>
     *登录时间
     * </pre>
     */
    public long getLoginTime() {
      return loginTime_;
    }

    // optional int64 loginIp = 5;
    public static final int LOGINIP_FIELD_NUMBER = 5;
    private long loginIp_;
    /**
     * <code>optional int64 loginIp = 5;</code>
     *
     * <pre>
     *登录IP
     * </pre>
     */
    public boolean hasLoginIp() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional int64 loginIp = 5;</code>
     *
     * <pre>
     *登录IP
     * </pre>
     */
    public long getLoginIp() {
      return loginIp_;
    }

    // optional string account = 6;
    public static final int ACCOUNT_FIELD_NUMBER = 6;
    private java.lang.Object account_;
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    public boolean hasAccount() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    public java.lang.String getAccount() {
      java.lang.Object ref = account_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          account_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string account = 6;</code>
     *
     * <pre>
     *帐号
     * </pre>
     */
    public com.google.protobuf.ByteString
        getAccountBytes() {
      java.lang.Object ref = account_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        account_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    // required int32 userType = 7;
    public static final int USERTYPE_FIELD_NUMBER = 7;
    private int userType_;
    /**
     * <code>required int32 userType = 7;</code>
     *
     * <pre>
     *用户类型
     * </pre>
     */
    public boolean hasUserType() {
      return ((bitField0_ & 0x00000040) == 0x00000040);
    }
    /**
     * <code>required int32 userType = 7;</code>
     *
     * <pre>
     *用户类型
     * </pre>
     */
    public int getUserType() {
      return userType_;
    }

    private void initFields() {
      userID_ = 0L;
      cuid_ = "";
      productId_ = 0;
      loginTime_ = 0L;
      loginIp_ = 0L;
      account_ = "";
      userType_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasUserID()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasCuid()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasProductId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasLoginTime()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasUserType()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt64(1, userID_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, getCuidBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, productId_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeInt64(4, loginTime_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeInt64(5, loginIp_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        output.writeBytes(6, getAccountBytes());
      }
      if (((bitField0_ & 0x00000040) == 0x00000040)) {
        output.writeInt32(7, userType_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(1, userID_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, getCuidBytes());
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, productId_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, loginTime_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(5, loginIp_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(6, getAccountBytes());
      }
      if (((bitField0_ & 0x00000040) == 0x00000040)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(7, userType_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wecon.restful.core.SessionState.UserInfo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.wecon.restful.core.SessionState.UserInfo prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.wecon.restful.core.UserInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.wecon.restful.core.SessionState.UserInfoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.wecon.restful.core.SessionState.internal_static_com_wecon_restful_core_UserInfo_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.wecon.restful.core.SessionState.internal_static_com_wecon_restful_core_UserInfo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.wecon.restful.core.SessionState.UserInfo.class, com.wecon.restful.core.SessionState.UserInfo.Builder.class);
      }

      // Construct using com.wecon.restful.core.SessionState.UserInfo.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        userID_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000001);
        cuid_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        productId_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        loginTime_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000008);
        loginIp_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000010);
        account_ = "";
        bitField0_ = (bitField0_ & ~0x00000020);
        userType_ = 0;
        bitField0_ = (bitField0_ & ~0x00000040);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.wecon.restful.core.SessionState.internal_static_com_wecon_restful_core_UserInfo_descriptor;
      }

      public com.wecon.restful.core.SessionState.UserInfo getDefaultInstanceForType() {
        return com.wecon.restful.core.SessionState.UserInfo.getDefaultInstance();
      }

      public com.wecon.restful.core.SessionState.UserInfo build() {
        com.wecon.restful.core.SessionState.UserInfo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.wecon.restful.core.SessionState.UserInfo buildPartial() {
        com.wecon.restful.core.SessionState.UserInfo result = new com.wecon.restful.core.SessionState.UserInfo(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.userID_ = userID_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.cuid_ = cuid_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.productId_ = productId_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.loginTime_ = loginTime_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000010;
        }
        result.loginIp_ = loginIp_;
        if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
          to_bitField0_ |= 0x00000020;
        }
        result.account_ = account_;
        if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
          to_bitField0_ |= 0x00000040;
        }
        result.userType_ = userType_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.wecon.restful.core.SessionState.UserInfo) {
          return mergeFrom((com.wecon.restful.core.SessionState.UserInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.wecon.restful.core.SessionState.UserInfo other) {
        if (other == com.wecon.restful.core.SessionState.UserInfo.getDefaultInstance()) return this;
        if (other.hasUserID()) {
          setUserID(other.getUserID());
        }
        if (other.hasCuid()) {
          bitField0_ |= 0x00000002;
          cuid_ = other.cuid_;
          onChanged();
        }
        if (other.hasProductId()) {
          setProductId(other.getProductId());
        }
        if (other.hasLoginTime()) {
          setLoginTime(other.getLoginTime());
        }
        if (other.hasLoginIp()) {
          setLoginIp(other.getLoginIp());
        }
        if (other.hasAccount()) {
          bitField0_ |= 0x00000020;
          account_ = other.account_;
          onChanged();
        }
        if (other.hasUserType()) {
          setUserType(other.getUserType());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasUserID()) {
          
          return false;
        }
        if (!hasCuid()) {
          
          return false;
        }
        if (!hasProductId()) {
          
          return false;
        }
        if (!hasLoginTime()) {
          
          return false;
        }
        if (!hasUserType()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.wecon.restful.core.SessionState.UserInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.wecon.restful.core.SessionState.UserInfo) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required int64 userID = 1;
      private long userID_ ;
      /**
       * <code>required int64 userID = 1;</code>
       */
      public boolean hasUserID() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int64 userID = 1;</code>
       */
      public long getUserID() {
        return userID_;
      }
      /**
       * <code>required int64 userID = 1;</code>
       */
      public Builder setUserID(long value) {
        bitField0_ |= 0x00000001;
        userID_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 userID = 1;</code>
       */
      public Builder clearUserID() {
        bitField0_ = (bitField0_ & ~0x00000001);
        userID_ = 0L;
        onChanged();
        return this;
      }

      // required string cuid = 2;
      private java.lang.Object cuid_ = "";
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public boolean hasCuid() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public java.lang.String getCuid() {
        java.lang.Object ref = cuid_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          cuid_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public com.google.protobuf.ByteString
          getCuidBytes() {
        java.lang.Object ref = cuid_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cuid_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public Builder setCuid(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        cuid_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public Builder clearCuid() {
        bitField0_ = (bitField0_ & ~0x00000002);
        cuid_ = getDefaultInstance().getCuid();
        onChanged();
        return this;
      }
      /**
       * <code>required string cuid = 2;</code>
       *
       * <pre>
       *登录设备的CUID
       * </pre>
       */
      public Builder setCuidBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        cuid_ = value;
        onChanged();
        return this;
      }

      // required int32 productId = 3;
      private int productId_ ;
      /**
       * <code>required int32 productId = 3;</code>
       *
       * <pre>
       *客户端应用ID
       * </pre>
       */
      public boolean hasProductId() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>required int32 productId = 3;</code>
       *
       * <pre>
       *客户端应用ID
       * </pre>
       */
      public int getProductId() {
        return productId_;
      }
      /**
       * <code>required int32 productId = 3;</code>
       *
       * <pre>
       *客户端应用ID
       * </pre>
       */
      public Builder setProductId(int value) {
        bitField0_ |= 0x00000004;
        productId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 productId = 3;</code>
       *
       * <pre>
       *客户端应用ID
       * </pre>
       */
      public Builder clearProductId() {
        bitField0_ = (bitField0_ & ~0x00000004);
        productId_ = 0;
        onChanged();
        return this;
      }

      // required int64 loginTime = 4;
      private long loginTime_ ;
      /**
       * <code>required int64 loginTime = 4;</code>
       *
       * <pre>
       *登录时间
       * </pre>
       */
      public boolean hasLoginTime() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>required int64 loginTime = 4;</code>
       *
       * <pre>
       *登录时间
       * </pre>
       */
      public long getLoginTime() {
        return loginTime_;
      }
      /**
       * <code>required int64 loginTime = 4;</code>
       *
       * <pre>
       *登录时间
       * </pre>
       */
      public Builder setLoginTime(long value) {
        bitField0_ |= 0x00000008;
        loginTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 loginTime = 4;</code>
       *
       * <pre>
       *登录时间
       * </pre>
       */
      public Builder clearLoginTime() {
        bitField0_ = (bitField0_ & ~0x00000008);
        loginTime_ = 0L;
        onChanged();
        return this;
      }

      // optional int64 loginIp = 5;
      private long loginIp_ ;
      /**
       * <code>optional int64 loginIp = 5;</code>
       *
       * <pre>
       *登录IP
       * </pre>
       */
      public boolean hasLoginIp() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>optional int64 loginIp = 5;</code>
       *
       * <pre>
       *登录IP
       * </pre>
       */
      public long getLoginIp() {
        return loginIp_;
      }
      /**
       * <code>optional int64 loginIp = 5;</code>
       *
       * <pre>
       *登录IP
       * </pre>
       */
      public Builder setLoginIp(long value) {
        bitField0_ |= 0x00000010;
        loginIp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int64 loginIp = 5;</code>
       *
       * <pre>
       *登录IP
       * </pre>
       */
      public Builder clearLoginIp() {
        bitField0_ = (bitField0_ & ~0x00000010);
        loginIp_ = 0L;
        onChanged();
        return this;
      }

      // optional string account = 6;
      private java.lang.Object account_ = "";
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public boolean hasAccount() {
        return ((bitField0_ & 0x00000020) == 0x00000020);
      }
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public java.lang.String getAccount() {
        java.lang.Object ref = account_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          account_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public com.google.protobuf.ByteString
          getAccountBytes() {
        java.lang.Object ref = account_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          account_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public Builder setAccount(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
        account_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public Builder clearAccount() {
        bitField0_ = (bitField0_ & ~0x00000020);
        account_ = getDefaultInstance().getAccount();
        onChanged();
        return this;
      }
      /**
       * <code>optional string account = 6;</code>
       *
       * <pre>
       *帐号
       * </pre>
       */
      public Builder setAccountBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000020;
        account_ = value;
        onChanged();
        return this;
      }

      // required int32 userType = 7;
      private int userType_ ;
      /**
       * <code>required int32 userType = 7;</code>
       *
       * <pre>
       *用户类型
       * </pre>
       */
      public boolean hasUserType() {
        return ((bitField0_ & 0x00000040) == 0x00000040);
      }
      /**
       * <code>required int32 userType = 7;</code>
       *
       * <pre>
       *用户类型
       * </pre>
       */
      public int getUserType() {
        return userType_;
      }
      /**
       * <code>required int32 userType = 7;</code>
       *
       * <pre>
       *用户类型
       * </pre>
       */
      public Builder setUserType(int value) {
        bitField0_ |= 0x00000040;
        userType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 userType = 7;</code>
       *
       * <pre>
       *用户类型
       * </pre>
       */
      public Builder clearUserType() {
        bitField0_ = (bitField0_ & ~0x00000040);
        userType_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.wecon.restful.core.UserInfo)
    }

    static {
      defaultInstance = new UserInfo(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.wecon.restful.core.UserInfo)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_wecon_restful_core_UserInfo_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_wecon_restful_core_UserInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022SessionState.proto\022\026com.wecon.restful." +
      "core\"\202\001\n\010UserInfo\022\016\n\006userID\030\001 \002(\003\022\014\n\004cui" +
      "d\030\002 \002(\t\022\021\n\tproductId\030\003 \002(\005\022\021\n\tloginTime\030" +
      "\004 \002(\003\022\017\n\007loginIp\030\005 \001(\003\022\017\n\007account\030\006 \001(\t\022" +
      "\020\n\010userType\030\007 \002(\005B&\n\026com.wecon.restful.c" +
      "oreB\014SessionState"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_wecon_restful_core_UserInfo_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_wecon_restful_core_UserInfo_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_wecon_restful_core_UserInfo_descriptor,
              new java.lang.String[] { "UserID", "Cuid", "ProductId", "LoginTime", "LoginIp", "Account", "UserType", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
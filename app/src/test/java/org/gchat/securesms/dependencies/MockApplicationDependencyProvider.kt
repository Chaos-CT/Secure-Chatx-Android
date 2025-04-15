package org.gchat.securesms.dependencies

import io.mockk.mockk
import org.signal.core.util.billing.BillingApi
import org.signal.core.util.concurrent.DeadlockDetector
import org.signal.libsignal.net.Network
import org.signal.libsignal.zkgroup.profiles.ClientZkProfileOperations
import org.signal.libsignal.zkgroup.receipts.ClientZkReceiptOperations
import org.gchat.securesms.components.TypingStatusRepository
import org.gchat.securesms.components.TypingStatusSender
import org.gchat.securesms.crypto.storage.SignalServiceDataStoreImpl
import org.gchat.securesms.database.DatabaseObserver
import org.gchat.securesms.database.PendingRetryReceiptCache
import org.gchat.securesms.jobmanager.JobManager
import org.gchat.securesms.megaphone.MegaphoneRepository
import org.gchat.securesms.messages.IncomingMessageObserver
import org.gchat.securesms.notifications.MessageNotifier
import org.gchat.securesms.payments.Payments
import org.gchat.securesms.push.SignalServiceNetworkAccess
import org.gchat.securesms.recipients.LiveRecipientCache
import org.gchat.securesms.revealable.ViewOnceMessageManager
import org.gchat.securesms.service.DeletedCallEventManager
import org.gchat.securesms.service.ExpiringMessageManager
import org.gchat.securesms.service.ExpiringStoriesManager
import org.gchat.securesms.service.PendingRetryReceiptManager
import org.gchat.securesms.service.ScheduledMessageManager
import org.gchat.securesms.service.TrimThreadsByDateManager
import org.gchat.securesms.service.webrtc.SignalCallManager
import org.gchat.securesms.shakereport.ShakeToReport
import org.gchat.securesms.util.EarlyMessageCache
import org.gchat.securesms.util.FrameRateTracker
import org.gchat.securesms.video.exo.GiphyMp4Cache
import org.gchat.securesms.video.exo.SimpleExoPlayerPool
import org.gchat.securesms.webrtc.audio.AudioManagerCompat
import org.whispersystems.signalservice.api.SignalServiceAccountManager
import org.whispersystems.signalservice.api.SignalServiceDataStore
import org.whispersystems.signalservice.api.SignalServiceMessageReceiver
import org.whispersystems.signalservice.api.SignalServiceMessageSender
import org.whispersystems.signalservice.api.account.AccountApi
import org.whispersystems.signalservice.api.archive.ArchiveApi
import org.whispersystems.signalservice.api.attachment.AttachmentApi
import org.whispersystems.signalservice.api.calling.CallingApi
import org.whispersystems.signalservice.api.cds.CdsApi
import org.whispersystems.signalservice.api.groupsv2.GroupsV2Operations
import org.whispersystems.signalservice.api.keys.KeysApi
import org.whispersystems.signalservice.api.link.LinkDeviceApi
import org.whispersystems.signalservice.api.message.MessageApi
import org.whispersystems.signalservice.api.payments.PaymentsApi
import org.whispersystems.signalservice.api.ratelimit.RateLimitChallengeApi
import org.whispersystems.signalservice.api.registration.RegistrationApi
import org.whispersystems.signalservice.api.services.DonationsService
import org.whispersystems.signalservice.api.services.ProfileService
import org.whispersystems.signalservice.api.storage.StorageServiceApi
import org.whispersystems.signalservice.api.username.UsernameApi
import org.whispersystems.signalservice.api.websocket.SignalWebSocket
import org.whispersystems.signalservice.internal.configuration.SignalServiceConfiguration
import org.whispersystems.signalservice.internal.push.PushServiceSocket
import java.util.function.Supplier

class MockApplicationDependencyProvider : AppDependencies.Provider {
  override fun providePushServiceSocket(signalServiceConfiguration: SignalServiceConfiguration, groupsV2Operations: GroupsV2Operations): PushServiceSocket {
    return mockk(relaxed = true)
  }

  override fun provideGroupsV2Operations(signalServiceConfiguration: SignalServiceConfiguration): GroupsV2Operations {
    return mockk(relaxed = true)
  }

  override fun provideSignalServiceAccountManager(authWebSocket: AccountApi, pushServiceSocket: PushServiceSocket, groupsV2Operations: GroupsV2Operations): SignalServiceAccountManager {
    return mockk(relaxed = true)
  }

  override fun provideSignalServiceMessageSender(
    authWebSocket: SignalWebSocket.AuthenticatedWebSocket,
    unauthWebSocket: SignalWebSocket.UnauthenticatedWebSocket,
    protocolStore: SignalServiceDataStore,
    pushServiceSocket: PushServiceSocket
  ): SignalServiceMessageSender {
    return mockk(relaxed = true)
  }

  override fun provideSignalServiceMessageReceiver(pushServiceSocket: PushServiceSocket): SignalServiceMessageReceiver {
    return mockk(relaxed = true)
  }

  override fun provideSignalServiceNetworkAccess(): SignalServiceNetworkAccess {
    return mockk(relaxed = true)
  }

  override fun provideRecipientCache(): LiveRecipientCache {
    return mockk(relaxed = true)
  }

  override fun provideJobManager(): JobManager {
    return mockk(relaxed = true)
  }

  override fun provideFrameRateTracker(): FrameRateTracker {
    return mockk(relaxed = true)
  }

  override fun provideMegaphoneRepository(): MegaphoneRepository {
    return mockk(relaxed = true)
  }

  override fun provideEarlyMessageCache(): EarlyMessageCache {
    return mockk(relaxed = true)
  }

  override fun provideMessageNotifier(): MessageNotifier {
    return mockk(relaxed = true)
  }

  override fun provideIncomingMessageObserver(webSocket: SignalWebSocket.AuthenticatedWebSocket): IncomingMessageObserver {
    return mockk(relaxed = true)
  }

  override fun provideTrimThreadsByDateManager(): TrimThreadsByDateManager {
    return mockk(relaxed = true)
  }

  override fun provideViewOnceMessageManager(): ViewOnceMessageManager {
    return mockk(relaxed = true)
  }

  override fun provideExpiringStoriesManager(): ExpiringStoriesManager {
    return mockk(relaxed = true)
  }

  override fun provideExpiringMessageManager(): ExpiringMessageManager {
    return mockk(relaxed = true)
  }

  override fun provideDeletedCallEventManager(): DeletedCallEventManager {
    return mockk(relaxed = true)
  }

  override fun provideTypingStatusRepository(): TypingStatusRepository {
    return mockk(relaxed = true)
  }

  override fun provideTypingStatusSender(): TypingStatusSender {
    return mockk(relaxed = true)
  }

  override fun provideDatabaseObserver(): DatabaseObserver {
    return mockk(relaxed = true)
  }

  override fun providePayments(paymentsApi: PaymentsApi): Payments {
    return mockk(relaxed = true)
  }

  override fun provideShakeToReport(): ShakeToReport {
    return mockk(relaxed = true)
  }

  override fun provideSignalCallManager(): SignalCallManager {
    return mockk(relaxed = true)
  }

  override fun providePendingRetryReceiptManager(): PendingRetryReceiptManager {
    return mockk(relaxed = true)
  }

  override fun providePendingRetryReceiptCache(): PendingRetryReceiptCache {
    return mockk(relaxed = true)
  }

  override fun provideProtocolStore(): SignalServiceDataStoreImpl {
    return mockk(relaxed = true)
  }

  override fun provideGiphyMp4Cache(): GiphyMp4Cache {
    return mockk(relaxed = true)
  }

  override fun provideExoPlayerPool(): SimpleExoPlayerPool {
    return mockk(relaxed = true)
  }

  override fun provideAndroidCallAudioManager(): AudioManagerCompat {
    return mockk(relaxed = true)
  }

  override fun provideDonationsService(pushServiceSocket: PushServiceSocket): DonationsService {
    return mockk(relaxed = true)
  }

  override fun provideProfileService(
    profileOperations: ClientZkProfileOperations,
    signalServiceMessageReceiver: SignalServiceMessageReceiver,
    authWebSocket: SignalWebSocket.AuthenticatedWebSocket,
    unauthWebSocket: SignalWebSocket.UnauthenticatedWebSocket
  ): ProfileService {
    return mockk(relaxed = true)
  }

  override fun provideDeadlockDetector(): DeadlockDetector {
    return mockk(relaxed = true)
  }

  override fun provideClientZkReceiptOperations(signalServiceConfiguration: SignalServiceConfiguration): ClientZkReceiptOperations {
    return mockk(relaxed = true)
  }

  override fun provideScheduledMessageManager(): ScheduledMessageManager {
    return mockk(relaxed = true)
  }

  override fun provideLibsignalNetwork(config: SignalServiceConfiguration): Network {
    return mockk(relaxed = true)
  }

  override fun provideBillingApi(): BillingApi {
    return mockk(relaxed = true)
  }

  override fun provideArchiveApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket, unauthWebSocket: SignalWebSocket.UnauthenticatedWebSocket, pushServiceSocket: PushServiceSocket): ArchiveApi {
    return mockk(relaxed = true)
  }

  override fun provideKeysApi(pushServiceSocket: PushServiceSocket): KeysApi {
    return mockk(relaxed = true)
  }

  override fun provideAttachmentApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket, pushServiceSocket: PushServiceSocket): AttachmentApi {
    return mockk(relaxed = true)
  }

  override fun provideLinkDeviceApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket): LinkDeviceApi {
    return mockk(relaxed = true)
  }

  override fun provideRegistrationApi(pushServiceSocket: PushServiceSocket): RegistrationApi {
    return mockk(relaxed = true)
  }

  override fun provideStorageServiceApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket, pushServiceSocket: PushServiceSocket): StorageServiceApi {
    return mockk(relaxed = true)
  }

  override fun provideAuthWebSocket(signalServiceConfigurationSupplier: Supplier<SignalServiceConfiguration>, libSignalNetworkSupplier: Supplier<Network>): SignalWebSocket.AuthenticatedWebSocket {
    return mockk(relaxed = true)
  }

  override fun provideUnauthWebSocket(signalServiceConfigurationSupplier: Supplier<SignalServiceConfiguration>, libSignalNetworkSupplier: Supplier<Network>): SignalWebSocket.UnauthenticatedWebSocket {
    return mockk(relaxed = true)
  }

  override fun provideAccountApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket): AccountApi {
    return mockk(relaxed = true)
  }

  override fun provideUsernameApi(unauthWebSocket: SignalWebSocket.UnauthenticatedWebSocket): UsernameApi {
    return mockk(relaxed = true)
  }

  override fun provideCallingApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket, pushServiceSocket: PushServiceSocket): CallingApi {
    return mockk(relaxed = true)
  }

  override fun providePaymentsApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket): PaymentsApi {
    return mockk(relaxed = true)
  }

  override fun provideCdsApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket): CdsApi {
    return mockk(relaxed = true)
  }

  override fun provideRateLimitChallengeApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket): RateLimitChallengeApi {
    return mockk(relaxed = true)
  }

  override fun provideMessageApi(authWebSocket: SignalWebSocket.AuthenticatedWebSocket, unauthWebSocket: SignalWebSocket.UnauthenticatedWebSocket): MessageApi {
    return mockk(relaxed = true)
  }
}

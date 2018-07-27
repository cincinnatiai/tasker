package com.cincinnatiai.tasker

import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock lateinit var view: MainContract.View
    lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        presenter = MainPresenter()
        presenter.view = view
    }

    @Test
    fun startAndShowHelloWorld() {
        // Given
        presenter.view = null

        // When
        presenter.start(view)

        // Then
        verify(view).showClickableHelloWorld()
        assertNotNull(presenter.view)
    }

    @Test
    fun helloClickedCheckPermissionsAndRequestPermissions() {
        // Given
        `when`(view.checkLocationsPermissions()).thenReturn(false)

        // When
        presenter.helloClicked()

        // Then
        verify(view).showFirstSuccessMessage()
        verify(view).requestPermissions()
        assertFalse(presenter.hasStartedBackgroundTask)
    }

    @Test
    fun helloClickedStartJob() {
        // Given
        `when`(view.checkLocationsPermissions()).thenReturn(true)

        // When
        presenter.helloClicked()

        // Then
        verify(view).showFirstSuccessMessage()
        verify(view).startLocationUpdates()
        assertTrue(presenter.hasStartedBackgroundTask)
    }

    @Test
    fun detachViewAndRemoveView() {
        // When
        presenter.detachView()

        // Then
        assertNull(presenter.view)
    }
}